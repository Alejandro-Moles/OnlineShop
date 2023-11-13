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
@Table(name="Roles")
@Data
@NoArgsConstructor
public class RoleModel {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID roleUuid;
	
	@Column(name = "role_type", unique = true)
	private String type;
	
	@Column(name ="role_isDeleted")
	private boolean isDeleted;
}
