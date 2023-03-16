/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ProjectTapHoa.controller;

import com.example.ProjectTapHoa.entity.Product;
import com.example.ProjectTapHoa.repo.ProductRepo;
import com.example.ProjectTapHoa.repo.TypeRepo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author dn1209
 */
@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    TypeRepo typeRepo;

    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("typeList", typeRepo.findAll());
        return "product/product_new.html";
    }

    @PostMapping("/new")
    public String add(
            @ModelAttribute("product") @Valid Product product,
            BindingResult bindingResult,@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
        if (!file.isEmpty()) {
			final String UPLOAD_FOLDER = "E:/hi/";

			String filename = file.getOriginalFilename();
			File newFile = new File(UPLOAD_FOLDER + filename);

			file.transferTo(newFile);

			product.setImage(filename);// save to db
		}

		if (bindingResult.hasErrors()) {
			return "product/add.html";
		}
        productRepo.save(product);
        return "redirect:/admin/product/all";
    }

    @GetMapping("/all")
    public String search(@RequestParam(name = "id", required = false) Integer id,
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

        return "product/search.html";
    }

    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", productRepo.findById(id).orElse(null));
        model.addAttribute("typeList", typeRepo.findAll());
        return "product/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("user") @Valid Product eProduct, BindingResult bindingResult)
            throws IllegalStateException, IOException {
        if (bindingResult.hasErrors()) {
            return "product/edit";
        }

        Product current = productRepo.findById(eProduct.getId()).orElse(null);

        // lay du lieu can update tu edit qua current, de tranh mat du lieu cu
        current.setName(eProduct.getName());
        current.setPrice(eProduct.getPrice());
        current.setDescription(eProduct.getDescription());
        current.setCreatedAt(eProduct.getCreatedAt());
        current.setType(eProduct.getType());
        productRepo.save(current);

        return "redirect:/admin/product/search";
    }
    @GetMapping("/delete") // ?id=1
	public String delete(@RequestParam("id") int id) {
		productRepo.deleteById(id);
		return "redirect:/admin/product/all";
	}
    @PostMapping("/upload-multi")
	public String add(@RequestParam("files") MultipartFile[] files) throws IllegalStateException, IOException {
		System.out.println(files.length);
		for (MultipartFile file : files)
			if (!file.isEmpty()) {
				final String UPLOAD_FOLDER = "E:/hi/";

				String filename = file.getOriginalFilename();
				File newFile = new File(UPLOAD_FOLDER + filename);

				file.transferTo(newFile);
			}

		return "redirect:/admin/product/search";
	}
    @GetMapping("/download")
	public void download(@RequestParam("filename") String filename, HttpServletResponse response) throws IOException {
		final String UPLOAD_FOLDER = "E:/hi/";

		File file = new File(UPLOAD_FOLDER + filename);

		Files.copy(file.toPath(), response.getOutputStream());
	}
    

}
