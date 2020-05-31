package com.kepa.springlibraryapp.user;


import com.kepa.springlibraryapp.verification.Token;
import com.kepa.springlibraryapp.verification.TokenNotFoundException;
import com.kepa.springlibraryapp.verification.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private UserService userService;
    private TokenRepository tokenRepository;

    @Autowired
    public UserController(UserService userService,TokenRepository tokenRepository) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
    }

    @GetMapping("/users")
    public String home(Model model, @RequestParam(value = "text", required = false) String text) {
        List<UserDto> users;
        if (text != null)
            users = userService.findAllByNameOrAuthor(text);
        else
            users = userService.findAll();
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute @Valid User user,
                          BindingResult bindResult) {
        if (bindResult.hasErrors())
            return "registerForm";

        try {
            userService.addWithDefaultRole(user);
        } catch (DuplicateException e) {
            bindResult.rejectValue("email", "error.user", "An account already exists for this email.");
            return "registerForm";
        }
        return "registerSuccess";
    }

    @GetMapping("/token")
    public String singup(@RequestParam String value) {
        Optional<Token> token = tokenRepository.findByValue(value);
        Token byValue = token.orElseThrow(TokenNotFoundException::new);
        User appUser = byValue.getAppUser();
        appUser.setEnabled(true);
        userService.save(appUser);
        return "activationSuccess";
    }

    @GetMapping("{userId}/delete")
    public String deleteUser(@PathVariable Long userId, Model model) {
        userService.delete(userId);
        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "redirect:/users";
    }

    @GetMapping("/user-edit")
    public String getUser(@RequestParam Long userId, Model model) {
        System.out.println(userId);
        Optional<UserDto> user = userService.findById(userId);
        model.addAttribute("userModel", user.get());
        return "panel/user-edit";
    }

    @PostMapping("/user-edit")
    public String updateUser(@ModelAttribute @Valid User user, @RequestParam Long userId, Model model) {
        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        userService.update(user, userId);
        return "redirect:/users";
    }
}
