package com.javaschool.onlineshop.models;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
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
}
