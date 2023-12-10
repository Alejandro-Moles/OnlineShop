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
public class OrderModel {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(nullable = false)
	private UUID orderUuid;

	@ManyToOne
	@JoinColumn(name = "order_payment_uuid")
	private PaymentModel payment;
	
	@ManyToOne
	@JoinColumn(name = "order_status_uuid", unique = false)
	private StatusModel status;
	
	@ManyToOne
	@JoinColumn(name = "order_delivery_uuid", unique = false)
	private DeliveryModel delivery;
	
	@ManyToOne
	@JoinColumn(name = "order_userAddress_uuid", unique = false)
	private UserAddressModel userAddress;
	
	@ManyToOne
	@JoinColumn(name = "order_user_uuid")
	private ShopUserModel shopUser;
	
	@Column(name = "order_pay_status")
	private Boolean pay_status;
				
	@Column(name = "order_isDeleted")
	private Boolean isDeleted;

	@Column(name = "order_date")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	//RELATIONS
	@OneToMany(mappedBy = "order", cascade= CascadeType.ALL)
	private List<OrderProductsModel> order_product;
}
