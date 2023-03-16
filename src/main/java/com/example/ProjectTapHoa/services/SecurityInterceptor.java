/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ProjectTapHoa.services;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;

/**
 *
 * @author dn1209
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor{
    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("!!!!!!!!!");
		System.out.println(request.getServletPath());
		System.out.println(request.getMethod());
		
		String path = request.getServletPath();

		if (path.equals("/user")) {
			//role admin
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
				List<String> roles = auth.getAuthorities().stream().map(p -> p.getAuthority())
						.collect(Collectors.toList());
				
				if (!roles.contains("Admin"))
					throw new AccessDeniedException("");
			} 
			
			throw new AccessDeniedException("");
		}
			
		return true;
}
}
