package com.example.demo.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.example.demo.entity.User;
@Component
public class RegisterUser {

	@Autowired
	UserService userService;
	
	
	public String registerNewUser(User user) {
		Random rand = new Random(); 
		user.setUserId(rand.nextInt());
		List<User> userList = findAll(user);
		List<String> dupList =userList.stream().filter(data->data.getUsername().equals(user.getUsername()))
				.map(mapper->mapper.getUsername()).collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(dupList)) {
			return "User Exist";
		}
	User savedUser=	userService.save(user);
	return savedUser.getUsername() !=null? "saved":"not saved";
	}
	
	public String authenticateUser(User user) {
		User savedUser=	userService.findById(user.getUserId()).orElse(new User());
		return savedUser.getUsername().equals("") ? "user not exist" : "authenticate";
	}
	
	private List<User> findAll(User user) {
		return userService.findAll();
	}
}
