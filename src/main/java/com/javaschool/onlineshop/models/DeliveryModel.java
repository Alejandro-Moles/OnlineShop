package com.javaschool.onlineshop.models;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Order_Delivery")
@Data
@NoArgsConstructor
public class DeliveryModel {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID deliveryUuid;
			
	@Column(name = "delivery_type", unique = true)
	private String type;
			
	@Column(name = "delivery_isDeleted")
	private Boolean isDeleted;

	@OneToMany(mappedBy = "delivery", cascade= CascadeType.ALL)
	private List<OrderModel> orders;
}
