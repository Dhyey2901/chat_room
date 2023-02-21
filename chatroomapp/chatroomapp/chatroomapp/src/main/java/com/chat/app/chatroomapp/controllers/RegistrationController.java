package com.chat.app.chatroomapp.controllers;

import com.chat.app.chatroomapp.Service.DefaultUserService;
import com.chat.app.chatroomapp.dto.UserRegisteredDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private DefaultUserService userService;

    public RegistrationController(DefaultUserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegisteredDTO userRegistrationDto()    //Replica of User class
    {
        return new UserRegisteredDTO();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping                //After clicking submit
    public String registerUserAccount(@ModelAttribute("user")
                                      UserRegisteredDTO registrationDto) {
        userService.save(registrationDto);
        return "redirect:/login";
    }
}
