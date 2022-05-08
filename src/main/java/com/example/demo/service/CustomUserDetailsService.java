package com.example.demo.service;

import com.example.demo.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.DAO.UserDAO;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {
   
    private UserDAO dao;
    private Collection<SimpleGrantedAuthority> authorities;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User user = null;
    	try {
			dao= new UserDAO();
			user = dao.find(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    	SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
    	authorities.add(authority);
  

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getSenha(), authorities);
    }
}
