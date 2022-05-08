package com.example.demo.security.services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.ERole;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.DAO.UserDAO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  UserDAO dao;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	 
	  User user=null;
	  
	try {
		dao= new UserDAO();
		user= dao.find(1);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return UserDetailsImpl.build(user);
  }

}
