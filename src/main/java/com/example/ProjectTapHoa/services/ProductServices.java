package com.example.ProjectTapHoa.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Repository;

import com.example.ProjectTapHoa.entity.Product;

public class ProductServices {
	private List<Product> products;
public Product findbyID(Integer id) {
	for(Product product : this.products) {
		if(product.getId().equals(id)) {
			return product;
		}
	}
	return null;
}
}
