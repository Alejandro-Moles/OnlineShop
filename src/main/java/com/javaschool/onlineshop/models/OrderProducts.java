package com.javaschool.onlineshop.models;
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
	private UUID orderProductsUuid;

	@ManyToOne
	@JoinColumn(name = "order_uuid")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "product_uuid")
	private Products product;
			
	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "isDeleted")
	private Boolean isDeleted;

}
