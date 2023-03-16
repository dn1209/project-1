/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ProjectTapHoa.repo;

import com.example.ProjectTapHoa.entity.ShipAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dn1209
 */
@Repository
public interface ShipAddressRepo extends JpaRepository<ShipAddress, Integer>{
    
}
