package com.javaschool.onlineshop.Models;
import jakarta.persistence.*;

@Entity
@Table(name="Order_has_Products")
public class OrderProducts {
	
	//COLUMNS
	@Id
	@ManyToOne
	@JoinColumn(name = "order_product")
	private Order OrderProduct;

	@Id
	@ManyToOne
	@JoinColumn(name = "product_orders")
	private Products ProductOrders;
			
	@Column(name = "quantity")
	private Integer quantity;

	public Order getOrderProduct() {
		return OrderProduct;
	}

	public void setOrderProduct(Order orderProduct) {
		OrderProduct = orderProduct;
	}

	public Products getProductOrders() {
		return ProductOrders;
	}

	public void setProductOrders(Products productOrders) {
		ProductOrders = productOrders;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
