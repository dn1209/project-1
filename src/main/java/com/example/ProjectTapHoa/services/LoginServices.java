/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ProjectTapHoa.services;

import com.example.ProjectTapHoa.entity.UserRole;
import com.example.ProjectTapHoa.repo.UserRepo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author dn1209
 */
@Service
public class LoginServices implements UserDetailsService{
    @Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.example.ProjectTapHoa.entity.User st = userRepo.findByUsername(username);

		if (st == null) {
			throw new UsernameNotFoundException("not found");
		}

		List<SimpleGrantedAuthority> list = 
				new ArrayList<SimpleGrantedAuthority>();

		for (UserRole role : st.getUserRoles()) {
			list.add(new SimpleGrantedAuthority(role.getRole()));
		}

		// tao user cua security // user dang nhap hien tai
		User currentUser = new User(
				username, st.getPassword(), list);

		return currentUser;
	}
}
