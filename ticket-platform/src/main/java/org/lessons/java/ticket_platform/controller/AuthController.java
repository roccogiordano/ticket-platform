package org.lessons.java.ticket_platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "/authentication/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/authentication/logout";
    }

}
