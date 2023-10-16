package com.javaschool.onlineshop.Models;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import lombok.Data;

import java.util.UUID;

@Entity
@Table(name="Order_has_Products")
@Data
@NoArgsConstructor
public class OrderProducts {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID order_products_uuid;

	@ManyToOne
	@JoinColumn(name = "order_product")
	private Order OrderProduct;


	@ManyToOne
	@JoinColumn(name = "product_orders")
	private Products ProductOrders;
			
	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "isDeleted")
	private Boolean isDeleted;

}
