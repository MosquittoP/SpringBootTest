package com.web.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.test.model.Product;
import com.web.test.repo.ProductDAO;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDAO productDao;
	
	@Override
	public boolean insert(Product product) {
		return productDao.insert(product) == 1;
	}

	@Override
	public boolean update(Product product) {
		return productDao.update(product) == 1;
	}

	@Override
	public boolean delete(int productno) {
		return productDao.delete(productno) == 1;
	}

	@Override
	public List<Product> getProduct() {
		return productDao.getProduct();
	}

	@Override
	public Product getProductByProductno(int productno) {
		return productDao.getProductByProductno(productno);
	}

}
