package com.web.test.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.test.model.Product;

@Mapper
public interface ProductDAO {
	int insert(Product product);
	int update(Product product);
	int delete(int productno);
	List<Product> getProduct();
	Product getProductByProductno(int productno);
}
