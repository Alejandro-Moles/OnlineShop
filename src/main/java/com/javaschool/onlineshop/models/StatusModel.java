package com.javaschool.onlineshop.models;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
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

	@OneToMany(mappedBy = "status", cascade= CascadeType.ALL)
	private List<OrderModel> orders;
}
