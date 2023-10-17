package com.javaschool.onlineshop.models;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Orders")
@Data
@NoArgsConstructor
public class Order {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID order_uuid;
	
	@OneToOne
	@JoinColumn(name = "order_payment_uuid")
	private Payment payment;
	
	@OneToOne
	@JoinColumn(name = "order_status_uuid")
	private Status status;
	
	@OneToOne
	@JoinColumn(name = "order_delivery_uuid")
	private Delivery delivery;
	
	@OneToOne
	@JoinColumn(name = "order_userAddress_uuid")
	private UserAddress userAddress;
	
	@ManyToOne
	@JoinColumn(name = "order_user_uuid")
	private ShopUser shopUser;
	
	@Column(name = "order_pay_status")
	private Boolean pay_status;
				
	@Column(name = "order_isDeleted")
	private Boolean isDeleted;

	@Column(name = "order_date")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	//RELATIONS
	@OneToMany(mappedBy = "order", cascade= CascadeType.ALL)
	private List<OrderProducts> order_product;
}
