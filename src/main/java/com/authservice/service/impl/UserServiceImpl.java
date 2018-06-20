package com.authservice.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.authservice.model.User;
import com.authservice.persistence.UserDao;
import com.authservice.service.IUserService;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, IUserService {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserServiceImpl:loadUserByUsername triggered.......... ");
		User user = userDao.findByUsername(username);
		if(user == null){
            System.out.println("UserServiceImpl:loadUserByUsername user not found.......... ");
			throw new UsernameNotFoundException("Invalid username or password.");
		}
        System.out.println("UserServiceImpl:loadUserByUsername setting Spring user.......... ");
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}


	@Override
	public User findOne(String username) {
        System.out.println("UserServiceImpl:findOne triggered.......... ");
		return userDao.findByUsername(username);
	}


}
