package com.chat.app.chatroomapp.Service;

import java.util.Collection;

import java.util.Set;
import java.util.stream.Collectors;

import com.chat.app.chatroomapp.dao.RoleRepository;
import com.chat.app.chatroomapp.dao.UserRepository;
import com.chat.app.chatroomapp.dto.UserRegisteredDTO;
import com.chat.app.chatroomapp.model.Role;
import com.chat.app.chatroomapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class DefaultUserServiceImpl implements DefaultUserService{
   @Autowired
	private UserRepository userRepo;
	
   @Autowired
  	private RoleRepository roleRepo;
  	
   @Autowired
	 JavaMailSender javaMailSender;
   
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


	//validating and all is done by this method
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	
		User user = userRepo.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRole()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
	}

	@Override
	public User save(UserRegisteredDTO userRegisteredDTO) {	//we land here after the user clicks submit
		Role role = roleRepo.findByRole("USER");		//used the role as user
		
		User user = new User();		//Constructing an object
		user.setEmail(userRegisteredDTO.getEmail_id());
		user.setName(userRegisteredDTO.getName());
		user.setPassword(passwordEncoder.encode(userRegisteredDTO.getPassword()));
		user.setRole(role);
		
		return userRepo.save(user);
	}

	//Generating random otp
	@Override
	public String generateOtp(User user) {
		try {
			int randomPIN = (int) (Math.random() * 9000) + 1000;
			user.setOtp(randomPIN);
			userRepo.save(user);		//Saving the user details
			SimpleMailMessage msg = new SimpleMailMessage();	//using simple mail message to send mail for otp
			msg.setFrom("projectsdp482@gmail.com");
			msg.setTo(user.getEmail());

			//use Java mail Sender to send the mail
			msg.setSubject("OTP for Two Factor Authentication");
			msg.setText("Hello \n\n" +"Your Login OTP :" + randomPIN + ".Please Verify. \n\n"+"Regards \n"+"ABC");

			javaMailSender.send(msg);
			
			return "success";
			}catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
	}

}
