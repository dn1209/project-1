/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ProjectTapHoa.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author dn1209
 */
@Entity
@Data
@Table(name="type")
public class Type {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
    
private String name;
    
@OneToMany(mappedBy = "type",fetch = FetchType.EAGER )
public List<Product> products;
}
