package org.example.pa_project;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.example.pa_project.Logs.LoggerInfo;
import org.example.pa_project.entities.Auction;
import org.example.pa_project.entities.FinalBid;
import org.example.pa_project.entities.Image;
import org.example.pa_project.entities.User;
import org.example.pa_project.security.JwtService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;



@RestController
@Tag(name = "ServerApp", description = "The main server application")
public class ServerApp {
    public static LoggerInfo loggerInfo = new LoggerInfo();
    private final RestTemplate restTemplate = new RestTemplate();
    private final JwtService jwtService = new JwtService();
    private String uri;
    private static final String UPLOAD_DIR = "src/main/resources/static/images";

    private String currentUri(String uri, String extension) {
        return uri.substring(0, uri.length() - extension.length());
    }

    @GetMapping("/callGetAuctions")
    public List<Auction> getAuctions(HttpServletRequest request) {

        uri = currentUri(request.getRequestURL().toString(), "/callGetAuctions");
        System.out.println(uri);
        String newUri = uri + "/auctions";

        ResponseEntity<List<Auction>> response = restTemplate.exchange(
                newUri, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    @PostMapping("/auctionsForm")
    public ResponseEntity<String> addAuction(@RequestHeader(name = "Authorization") String token,
                                             @RequestParam("title") String title,
                                             @RequestParam("description") String description,
                                             @RequestParam("price") String price,
                                             @RequestParam("deadline") String deadline,
                                             @RequestParam("time") String time,
                                             @RequestParam("upload-image[]") List<MultipartFile> pictures,
                                             HttpServletRequest request) {
        uri = currentUri(request.getRequestURL().toString(), "/auctionsForm");
        System.out.println(uri);

        int auctionId = saveAuction(title, description, price, deadline, time, token);

        if (pictures.size() == 1 && pictures.getFirst().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return saveImages(pictures, auctionId);
        }
    }

    private int saveAuction(String title, String description, String price, String deadline, String time, String token) {
        Auction auction = new Auction();
        auction.setTitle(title);
        auction.setDescription(description);
        auction.setInitialPrice(Integer.parseInt(price));
        auction.setDeadline(java.sql.Date.valueOf(deadline));
        auction.setTime(java.time.LocalTime.parse(time));

        int userId = getUserId(token);
        auction.setUsersId(userId);
        System.out.println(auction);

        String newUri = uri + "/auctions";
        System.out.println(newUri);

        HttpEntity<Auction> requestEntity = new HttpEntity<>(auction);
        ResponseEntity<Auction> responseEntity = restTemplate.exchange(
                newUri, HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody().getId();
    }

    private ResponseEntity<String> saveImages(List<MultipartFile> pictures, int auctionId) {
        for (MultipartFile picture : pictures) {
            try {

//                Save the file somewhere
//                byte[] bytes = picture.getBytes();
//                String extension = picture.getContentType().split("/")[1];
//                System.out.println(extension);

                Image image = new Image();
                image.setAuctionsId(auctionId);
                image.setExtension("");

                String newUri = uri + "/imagesDatabase";

                HttpEntity<Image> requestEntity = new HttpEntity<>(image);
                ResponseEntity<Image> responseEntityImage = restTemplate.exchange(
                        newUri, HttpMethod.POST, requestEntity,
                        new ParameterizedTypeReference<>() {
                        });
                ResponseEntity<List<Image>> responseEntity = restTemplate.exchange(
                        newUri, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });
                List<Image> images = responseEntity.getBody();

                int imageId = images.stream()
                        .filter(counter -> counter.getAuctionsId() == auctionId)
                        .map(Image::getId)
                        .sorted(Comparator.reverseOrder())
                        .findFirst()
                        .orElse(-1);
                System.out.println(imageId);

                CloudinaryUploader uploader = new CloudinaryUploader();

                String imageUrl = uploader.uploadFile(picture.getBytes(), String.valueOf(imageId));
                System.out.println("Image URL: " + imageUrl);

                image.setExtension(imageUrl);

                newUri = newUri + "/" + imageId;

                HttpEntity<Image> requestEntity1 = new HttpEntity<>(image);
                ResponseEntity<Image> responseEntityImage1 = restTemplate.exchange(
                        newUri, HttpMethod.PUT, requestEntity1,
                        new ParameterizedTypeReference<>() {
                        });

//                Path filePath = Paths.get(UPLOAD_DIR, "/" + String.valueOf(imageId) + "." + extension);
//                System.out.println("*" + filePath.toString() + "*");
//
//                File imageFile = new File(filePath.toString());
//                imageFile.createNewFile();
//                Files.write(filePath, picture.getBytes());
//
//                // Use bytes and fileName as needed (e.g., save to the database or filesystem)
//                // System.out.println("Received picture: " + fileName + " (" + bytes.length + " bytes)");

            } catch (IOException e) {
                loggerInfo.logException(e);
                e.printStackTrace();
                return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("File upload successful", HttpStatus.OK);
    }

    @PostMapping("/bidsForm")
    public ResponseEntity<String> addBid(@RequestHeader(name = "Authorization") String token, @RequestBody FinalBid bid,
                                         HttpServletRequest request) {
        uri = currentUri(request.getRequestURL().toString(), "/bidsForm");
        System.out.println(uri);
        System.out.println(bid);
        int userId = getUserId(token);
        bid.setUsersId(userId);

        String newUri = uri + "/bids";
        System.out.println(newUri);

        HttpEntity<FinalBid> requestEntity = new HttpEntity<>(bid);
        return restTemplate.exchange(
                newUri, HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<>() {
                });
    }

    private int getUserId(String token) {
        token = token.split(" ")[1];
        String userName = jwtService.extractUsername(token);

        String newNameUri = uri + "/users";
        System.out.println(newNameUri);

        ResponseEntity<List<User>> response = restTemplate.exchange(
                newNameUri, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        var users = response.getBody();
        User user = users.stream()
                .filter(u -> u.getUsername().equals(userName))
                .findFirst()
                .orElse(null);
        return user.getId();
    }

    @PutMapping("/callUpdateAuction")
    public ResponseEntity<String> updateAuction(Auction auction, HttpServletRequest request) {
        uri = currentUri(request.getRequestURL().toString(), "/callUpdateAuction");
        System.out.println(uri);
        String newUri = uri + "/auctions";
        HttpEntity<Auction> requestEntity = new HttpEntity<>(auction);
        return restTemplate.exchange(
                newUri, HttpMethod.PUT, requestEntity,
                new ParameterizedTypeReference<>() {
                });
    }

    @DeleteMapping("/callDeleteAuction")
    public ResponseEntity<String> deleteAuction(Auction auction, HttpServletRequest request) {
        uri = currentUri(request.getRequestURL().toString(), "/callDeleteAuction");
        System.out.println(uri);
        String newUri = uri + "/auctions";
        HttpEntity<Auction> requestEntity = new HttpEntity<>(auction);
        return restTemplate.exchange(
                newUri, HttpMethod.DELETE, requestEntity,
                new ParameterizedTypeReference<>() {
                });
    }
}
