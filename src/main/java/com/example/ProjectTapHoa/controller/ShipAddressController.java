/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ProjectTapHoa.controller;

import com.example.ProjectTapHoa.entity.ShipAddress;
import com.example.ProjectTapHoa.repo.ShipAddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dn1209
 */
@Controller
@RequestMapping("/admin/ship")
public class ShipAddressController {
    @Autowired
    ShipAddressRepo shipAddressRepo;
    
    
   
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('Admin')")
    public String search(@ModelAttribute ShipAddress shipAddress,
    		Model model,
    		@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "name", required = false) String name) {
		
		
		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ShipAddress> list = shipAddressRepo.findAll(pageable);
		
		model.addAttribute("shipAddressList", list.getContent());
		model.addAttribute("totalpage", list.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("size", size);

		
		return "shipAddress/search.html";
        //tuong ung foward cua servlet
    }
}
