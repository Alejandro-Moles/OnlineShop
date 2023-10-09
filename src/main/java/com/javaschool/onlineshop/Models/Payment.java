package com.javaschool.onlineshop.Models;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="Order_Payment")
public class Payment {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID payment_uuid;
	
	@Column(name = "payment_type")
	private String type;
	
	@Column(name = "payment_isDeleted")
	private Boolean isDeleted;

	
	//GETTERS AND SETTERS
	public UUID getPaymentUuid() {
		return payment_uuid;
	}

	public String getPaymentType() {
		return type;
	}

	public Boolean getPaymentIsDeleted() {
		return isDeleted;
	}
}
