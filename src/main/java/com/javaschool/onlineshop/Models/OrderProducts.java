package com.javaschool.onlineshop.Models;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import lombok.Data;

@Entity
@Table(name="Order_has_Products")
@Data
@NoArgsConstructor
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

}
