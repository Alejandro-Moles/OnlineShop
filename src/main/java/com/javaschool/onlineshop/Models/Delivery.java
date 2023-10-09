package com.javaschool.onlineshop.Models;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="Order_Delivery")
public class Delivery {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID delivery_uuid;
			
	@Column(name = "delivery_type")
	private String type;
			
	@Column(name = "delivery_isDeleted")
	private Boolean isDeleted;

			
	//GETTERS AND SETTERS
	public UUID getDeliveryUuid() {
		return delivery_uuid;
	}

	public String getDeliveryType() {
		return type;
	}

	public Boolean getDeliveryIsDeleted() {
		return isDeleted;
	}
}
