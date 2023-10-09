package com.javaschool.onlineshop.Models;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="Roles")
public class Role {
	
	//COLUMNS
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID role_uuid; 
	
	@Column(name = "role_type")
	private String type;
	
	@Column(name ="role_isDeleted")
	private boolean isDeleted;
	
	//GETTERS AND SETTERS
	public UUID getRoleUuid() {
		return role_uuid;
	}
	
	public String getRoleType() {
		return type;
	}
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}
}
