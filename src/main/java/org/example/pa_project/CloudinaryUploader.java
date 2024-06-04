package org.example.pa_project;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * The class witch upload a photo in Cloudinary and return the link of the image
 */
public class CloudinaryUploader {

    private final Cloudinary cloudinary;

    public CloudinaryUploader() {
        String cloudName = System.getenv("CLOUDINARY_CLOUD_NAME");
        String apiKey = System.getenv("CLOUDINARY_API_KEY");
        String apiSecret = System.getenv("CLOUDINARY_API_SECRET");

        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    /**
     * Upload the file and return the link
     * @param fileBytes - the bytes of the selected image
     * @param publicId - the given name of the photo
     * @return - the link
     */
    public String uploadFile(byte[] fileBytes, String publicId) {
        try {

            Map options = ObjectUtils.asMap("public_id", publicId);
            Map uploadResult = cloudinary.uploader().upload(fileBytes, options);

            System.out.println("Successfully uploaded file: " + uploadResult.get("secure_url"));
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to upload file to Cloudinary: " + e.getMessage());
            return null;
        }
    }

//    public static void main(String[] args) throws IOException {
//        CloudinaryUploader uploader = new CloudinaryUploader();
//        File file = new File("D:/Temporary Files/null.jpg");
//        byte[] fileBytes = FileUtils.readFileToByteArray(file);
//        String imageUrl = uploader.uploadFile(fileBytes, "null");
//        System.out.println("Image URL: " + imageUrl);
//    }
}
