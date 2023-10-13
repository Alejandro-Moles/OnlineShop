package com.javaschool.onlineshop.Models;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Order_Payment")
@Data
@NoArgsConstructor
public class Payment {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID payment_uuid;
	
	@Column(name = "payment_type", unique = true)
	private String type;
	
	@Column(name = "payment_isDeleted")
	private Boolean isDeleted;
}
