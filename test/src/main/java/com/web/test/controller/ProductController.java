package com.web.test.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.test.model.Product;
import com.web.test.service.ProductService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("product")
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	ProductService productService;
	
	@GetMapping
	@ApiOperation(value = "모든 상품 조회")
	public ResponseEntity<List<Product>> productList() {
		logger.debug("productList");
		List<Product> list = productService.getProduct();
		if (list != null) {
			for (Product p : list)
				p.setImage("image/" + p.getImage());
		}
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "상품 추가")
	public ResponseEntity<String> insertProduct(HttpServletRequest request) {
		logger.debug("insertProduct");
		MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
		MultipartFile file = mrequest.getFile("image");
		Product product = new Product();
		product.setName(request.getParameter("name"));
		product.setKinds(Integer.parseInt(request.getParameter("kinds")));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setDescription(request.getParameter("description"));
		if (file != null) {
			String time = "";
			time += System.currentTimeMillis();
			String filename = file.getOriginalFilename();
			try {
				file.transferTo(new File("image/" + time + filename));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			product.setImage(time + filename);
		}
		else
			product.setImage("null.png");
		if (productService.insert(product))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("{productno}")
	@ApiOperation(value = "상품 정보 조회")
	public Object productDetail(@PathVariable int productno) {
		logger.debug("productDetail");
		Product product = productService.getProductByProductno(productno);
		if (product != null) {
			product.setImage("image/" + product.getImage());
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("{productno}")
	@ApiOperation(value = "상품 정보 수정")
	public ResponseEntity<String> updateProduct(@RequestBody Product product, HttpServletRequest request) {
		logger.debug("updateProduct");
		MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
		MultipartFile file = mrequest.getFile("image");
		Product nowProduct = productService.getProductByProductno(product.getProductno());
		if(file != null) {
			String time = "";
			time += System.currentTimeMillis();
			String filename = file.getOriginalFilename();
			try {
				file.transferTo(new File("image/" + time + filename));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			product.setImage(time + filename);
			if (!nowProduct.getImage().equals("null.png")) {
				File nowFile = new File("image/" + nowProduct.getImage());
				if (nowFile.exists())
					nowFile.delete();
			}
		}
		else {
			product.setImage("null.png");
			if (!nowProduct.getImage().equals("null.png")) {
				File nowFile = new File("image/" + nowProduct.getImage());
				if (nowFile.exists())
					nowFile.delete();
			}
		}
		if (productService.update(product))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("{productno}")
	@ApiOperation(value = "상품 삭제")
	public ResponseEntity<String> deleteProduct(@PathVariable int productno) {
		logger.debug("deleteProduct");
		Product product = productService.getProductByProductno(productno);
		if (!product.getImage().equals("null.png")) {
			File file = new File("image/" + product.getImage());
			if (file.exists())
				file.delete();
		}
		if (productService.delete(productno))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}
}
