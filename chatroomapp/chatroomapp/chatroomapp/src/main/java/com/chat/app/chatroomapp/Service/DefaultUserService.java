package com.chat.app.chatroomapp.Service;

import com.chat.app.chatroomapp.dto.UserRegisteredDTO;
import com.chat.app.chatroomapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.chat.app.chatroomapp.dto.UserRegisteredDTO;

public interface DefaultUserService extends UserDetailsService{

	User save(UserRegisteredDTO userRegisteredDTO);

	String generateOtp(User user);



	
}
