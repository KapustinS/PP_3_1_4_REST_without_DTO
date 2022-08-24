package academy.kata.PP_3_1_4_REST.controllers;

import academy.kata.PP_3_1_4_REST.model.User;
import academy.kata.PP_3_1_4_REST.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@CrossOrigin
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.
                showByEmail(userDetails.getUsername())
                .orElse(null);

        return ResponseEntity.ok(user);
    }

}
