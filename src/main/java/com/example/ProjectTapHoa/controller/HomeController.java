/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ProjectTapHoa.controller;

import com.example.ProjectTapHoa.entity.Product;
import com.example.ProjectTapHoa.entity.User;
import com.example.ProjectTapHoa.entity.UserRole;
import com.example.ProjectTapHoa.repo.ProductRepo;
import com.example.ProjectTapHoa.repo.TypeRepo;
import com.example.ProjectTapHoa.repo.UserRoleRepo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dn1209
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    TypeRepo typeRepo;
    
    @Autowired
    UserRoleRepo userRoleRepo;
    
   
    
    @GetMapping("/shopage")
    public String searchPublic(@RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "nameType", required = false) String nameType,
            @RequestParam(name = "TypeId", required = false) Integer categoryId,
            @RequestParam(name = "TypeName", required = false) String categoryName,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page, Model model) {

        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        Pageable pageable = PageRequest.of(page, size);

        if (id != null) {
            List<Product> products = productRepo.findAllById(Arrays.asList(id));

            model.addAttribute("totalPage", 1);
            model.addAttribute("count", products.size());
            model.addAttribute("productList", products);
        } else {
            Page<Product> pageRS = null;

            if (StringUtils.hasText(name)) {
                pageRS = productRepo.searchByName("%" + name + "%", pageable);
            } else if (StringUtils.hasText(nameType)) {
                pageRS = productRepo.searchByTypeName("%" + nameType + "%", pageable);
            } else {
                pageRS = productRepo.findAll(pageable);
            }

            model.addAttribute("totalPage", pageRS.getTotalPages());
            model.addAttribute("count", pageRS.getTotalElements());
            model.addAttribute("productList", pageRS.getContent());
        }

        // luu lai du lieu set sang view lai
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("nameType", nameType);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "public_user/search.html";
    }
    @GetMapping("/download")
	public void download(@RequestParam("filename") String filename, HttpServletResponse response) throws IOException {
		final String UPLOAD_FOLDER = "N:/hi/";

		File file = new File(UPLOAD_FOLDER + filename);

		Files.copy(file.toPath(), response.getOutputStream());
	}
    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutSuccessfulPage";
	}
}
