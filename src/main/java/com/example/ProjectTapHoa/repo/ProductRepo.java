/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ProjectTapHoa.repo;

import com.example.ProjectTapHoa.entity.Product;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author dn1209
 */
public interface ProductRepo extends JpaRepository<Product, Integer> {

    //tìm theo tên user
    @Query("SELECT u FROM Product u WHERE u.name LIKE :x ")
    Page<Product> searchByName(@Param("x") String s, Pageable pageable);

       @Query("SELECT u FROM Product u JOIN u.type d WHERE d.name LIKE :x")
	Page<Product> searchByTypeName(@Param("x") String dName, Pageable pageable);
        
        
}

