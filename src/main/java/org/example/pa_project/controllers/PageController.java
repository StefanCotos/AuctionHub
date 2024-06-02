package org.example.pa_project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/about.html")
    public String getAbout() {
        return "about";
    }

    @GetMapping("/admin.html")
    public String getAdmin() {
        return "admin";
    }

    @GetMapping("/auction-info.html")
    public String getAnimal() {
        return "auction-info";
    }

    @GetMapping("/auctions.html")
    public String getAuctions() {
        return "auctions";
    }

    @GetMapping("/auction-ma.html")
    public String getAuctionMa() {
        return "auction-ma";
    }

    @GetMapping("/contact.html")
    public String getContact() {
        return "contact";
    }

    @GetMapping("/forget-up.html")
    public String getForgetUp() {
        return "forget-up";
    }

    @GetMapping("/help.html")
    public String getHelp() {
        return "help";
    }

    @GetMapping("/my-auctions.html")
    public String getMyAuctions() {
        return "my-auctions";
    }

    @GetMapping("/login.html")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/signup.html")
    public String getRegister() {
        return "signup";
    }
}
