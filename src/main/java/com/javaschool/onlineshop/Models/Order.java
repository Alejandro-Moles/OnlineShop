package com.javaschool.onlineshop.Models;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
	
	//RELATIONS
	@OneToMany(mappedBy = "OrderProduct", cascade= CascadeType.ALL)
	private List<OrderProducts> order_product;
}
