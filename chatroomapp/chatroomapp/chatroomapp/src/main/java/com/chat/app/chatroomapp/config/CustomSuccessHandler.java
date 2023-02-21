package com.chat.app.chatroomapp.config;

import java.io.IOException;


import com.chat.app.chatroomapp.Service.DefaultUserService;
import com.chat.app.chatroomapp.dao.UserRepository;
import com.chat.app.chatroomapp.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserRepository userRepo;

    @Autowired
    DefaultUserService userService;

    //This happens after login which is successful
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String redirectUrl = null;
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepo.findByEmail(username);
        String output = userService.generateOtp(user);
        if (output == "success")
            redirectUrl = "/login/otpVerification";    //redirects for otp verification

        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

}
