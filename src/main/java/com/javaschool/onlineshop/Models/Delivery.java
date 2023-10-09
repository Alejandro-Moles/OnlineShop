package com.javaschool.onlineshop.Models;
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
public class Delivery {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID delivery_uuid;
			
	@Column(name = "delivery_type")
	private String type;
			
	@Column(name = "delivery_isDeleted")
	private Boolean isDeleted;
}
