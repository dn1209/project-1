/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ProjectTapHoa.controller;

import com.example.ProjectTapHoa.entity.User;
import com.example.ProjectTapHoa.repo.UserRepo;
import com.example.ProjectTapHoa.repo.UserRoleRepo;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dn1209
 */
@Controller
@RequestMapping("/user")

public class UserController {

    
    
    @Autowired
    UserRepo userRepo;

    @Autowired
    UserRoleRepo userRoleRepo;

    @GetMapping("/new")
   // @PreAuthorize("hasAuthority('Admin')")

    public String add(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("userRoleList", userRoleRepo.findAll());
        return "user/user_new.html";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        userRepo.save(user);
        return "redirect:/home/shopage";

    }
    
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('Admin')")
    public String search(
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "userRoleId", required = false) Integer userRoleId,
            @RequestParam(name = "userRoleRole", required = false) String userRoleRole,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page, Model model) {

        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        Pageable pageable = PageRequest.of(page, size);

        Page<User> pageRS = userRepo.findAll(pageable);

        model.addAttribute("totalPage", pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("userList", pageRS.getContent());

        // luu lai du lieu set sang view lai
        model.addAttribute("id", id);
        model.addAttribute("name", name);

        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "user/search.html";
    }
}
