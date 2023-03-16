/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ProjectTapHoa.controller;

import com.example.ProjectTapHoa.entity.Cart;
import com.example.ProjectTapHoa.entity.Product;
import com.example.ProjectTapHoa.repo.ProductRepo;
import com.example.ProjectTapHoa.services.ProductServices;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dn1209
 */
@Controller
public class CartController {
    
    @Autowired
    ProductRepo productRepo;
  
    
     @GetMapping(value = { "view-cart/{id}.html" })
	public String cart(ModelMap mm,HttpServletRequest request, @RequestParam(name = "id") Integer id, HttpSession session,
                @RequestParam(name = "quantity", required = false) Integer quantity) {
    	 HashMap<Integer, Cart> cartItems = (HashMap<Integer,Cart>) session.getAttribute("itemCarts");
    	 if(cartItems == null) {
    		 cartItems = new HashMap<>();
    	 }
    	 ProductServices productServices = new ProductServices();
    	 Product product = productServices.findbyID(id);
    	 if(product != null) {
    		 if(cartItems.containsKey(id)){
    			 Cart item = cartItems.get(id);
    			 item.setProduct(product);
    			 item.setQuantity(item.getQuantity() +1);
    			 cartItems.put(id, item);
    		 }
    		 else {
    			 Cart item = new Cart();
    			 item.setProduct(product);
    			 item.setQuantity(1);
    			 cartItems.put(id, item);
    		 }
    	 }
    	 session.setAttribute("itemCarts", cartItems);
    	 session.setAttribute("cartTotal", totalPrice(cartItems));
    	 session.setAttribute("cartSize", cartItems.size());
//		Product product = productRepo.getById(id);
//                quantity = quantity == null ? 1 : quantity;
//                
//		if (session.getAttribute("cart") == null) {
//                    BillDetail billDetail = new BillDetail();
//                    billDetail.setQuanty(quantity);
//                    billDetail.setTotal(product.getPrice());
//                    billDetail.setProductId(product);
//			Map<Integer, BillDetail> map = new HashMap<Integer, BillDetail>();
//			map.put(id, billDetail);
//
//			session.setAttribute("cart", map);
//
//		} else {
//			Map<Integer, BillDetail> map = (Map<Integer, BillDetail>) session.getAttribute("cart");
//			BillDetail billDetail = map.get(id);
//			if (billDetail == null) {
//				billDetail = new BillDetail();
//                                billDetail.setQuanty(quantity);
//                                billDetail.setTotal(product.getPrice());
//                                billDetail.setProductId(product);
//				map.put(id, billDetail);
//			} else {
//                            billDetail.setQuanty(billDetail.getQuanty() + quantity);
//			}
//			session.setAttribute("cart", map);
//
//		}

		return "redirect:/view-cart";
	}
     @GetMapping(value = { "/view-carta" })
 	public String view(HttpServletRequest request, HttpSession session) {
    	 HashMap<Integer, Cart> cartItems = (HashMap<Integer,Cart>) session.getAttribute("itemCarts");
    	 if(cartItems == null) {
    		 cartItems = new HashMap<>();
    	 }
    	 session.setAttribute("itemCarts", cartItems);
 		return "redirect:/view-cart";

 	}
        @GetMapping(value = { "/delete-to-cart" })
	public String delete(HttpServletRequest request, HttpSession session, @RequestParam(name = "id") int id) {
		if(session.getAttribute("cart") != null) {
			Map<Integer, Cart> map = (Map<Integer, Cart>) session.getAttribute("cart");
			map.remove(id);
			
		}
		return "redirect:/view-cart";

	}
        
        @GetMapping(value = { "/clear-cart" })
	public String deletehome(HttpSession session) {
		session.removeAttribute("cart");
		return "redirect:/view-cart";
	}
        public double totalPrice(HashMap<Integer, Cart> cartItems) {
        	int count =0;
        	for(Map.Entry<Integer, Cart> list : cartItems.entrySet()) {
        		count += list.getValue().getProduct().getPrice() * list.getValue().getQuantity();
        	}
			return count;
        	
        }
        
}
