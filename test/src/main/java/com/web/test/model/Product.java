package com.web.test.model;

public class Product {
	int productno, kinds;
	String name;
	int price;
	String description, image;
	public Product() {}
	public Product(int productno, int kinds, String name, int price, String description, String image) {
		this.productno = productno;
		this.kinds = kinds;
		this.name = name;
		this.price = price;
		this.description = description;
		this.image = image;
	}
	public int getProductno() {
		return productno;
	}
	public void setProductno(int productno) {
		this.productno = productno;
	}
	public int getKinds() {
		return kinds;
	}
	public void setKinds(int kinds) {
		this.kinds = kinds;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Product [productno=" + productno + ", kinds=" + kinds + ", name=" + name + ", price=" + price + ", description="
				+ description + ", image=" + image + "]";
	}
}
