package com.javaschool.onlineshop.Models;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="Orders")
public class Order {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	private User_Address userAddress;
	
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

	public UUID getOrder_uuid() {
		return order_uuid;
	}

	public void setOrder_uuid(UUID order_uuid) {
		this.order_uuid = order_uuid;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public User_Address getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(User_Address userAddress) {
		this.userAddress = userAddress;
	}

	public ShopUser getShopUser() {
		return shopUser;
	}

	public void setShopUser(ShopUser shopUser) {
		this.shopUser = shopUser;
	}

	public Boolean getPay_status() {
		return pay_status;
	}

	public void setPay_status(Boolean pay_status) {
		this.pay_status = pay_status;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<OrderProducts> getOrder_product() {
		return order_product;
	}

	public void setOrder_product(List<OrderProducts> order_product) {
		this.order_product = order_product;
	}
}
