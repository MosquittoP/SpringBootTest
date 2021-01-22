package com.web.test.service;

import java.util.List;

import com.web.test.model.Product;

public interface ProductService {
	boolean insert(Product product);
	boolean update(Product product);
	boolean delete(int productno);
	List<Product> getProduct();
	Product getProductByProductno(int productno);
}
