package academy.kata.PP_3_1_4_REST.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/admin")
    public String adminPage() {
        return "/admin/adminPage";
    }

    @GetMapping("/user")
    public  String userPage() {
        return "/user";
    }
}
