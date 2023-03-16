/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ProjectTapHoa.repo;

import com.example.ProjectTapHoa.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author dn1209
 */
public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
    @Query("SELECT ur FROM UserRole ur WHERE ur.role LIKE :x ")
	Page<UserRole> searchByRole(@Param("x") String s, Pageable pageable);

	@Query("SELECT ur FROM UserRole ur JOIN ur.user u" + " WHERE u.id = :x ")
	Page<UserRole> searchByUserId(@Param("x") int userId, Pageable pageable);

	@Query("SELECT ur FROM UserRole ur JOIN ur.user u" + " WHERE u.name LIKE :x ")
	Page<UserRole> searchByUserName(@Param("x") String uName, Pageable pageable);

	@Query("SELECT ur FROM UserRole ur JOIN ur.user u" + " WHERE u.name LIKE :x AND u.id = :x ")
	Page<UserRole> searchByNameAndId(@Param("x") String uName, @Param("x") int id, Pageable pageable);
}
