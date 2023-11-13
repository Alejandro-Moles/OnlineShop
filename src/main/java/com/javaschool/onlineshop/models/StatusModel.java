package com.javaschool.onlineshop.models;
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
@Table(name="Order_Status")
@Data
@NoArgsConstructor
public class StatusModel {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID statusUuid;
		
	@Column(name = "status_type", unique = true)
	private String type;
		
	@Column(name = "status_isDeleted")
	private Boolean isDeleted;
}
