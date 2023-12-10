package com.javaschool.onlineshop.models;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Order_Payment")
@Data
@NoArgsConstructor
public class PaymentModel {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID paymentUuid;
	
	@Column(name = "payment_type", unique = true)
	private String type;
	
	@Column(name = "payment_isDeleted")
	private Boolean isDeleted;

	@OneToMany(mappedBy = "payment", cascade= CascadeType.ALL)
	private List<OrderModel> orders;
}
