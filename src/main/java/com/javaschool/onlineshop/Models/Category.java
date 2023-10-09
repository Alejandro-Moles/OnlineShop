package com.javaschool.onlineshop.Models;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="Category")
public class Category {
	//COLUMNS
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID category_uuid;

	@Column(name = "category_type")
	private String type;
	
	@Column(name = "category_isDeleted")
	private Boolean isDeleted;
	
	
	//GETTERS AND SETTERS
	public UUID getCategoryUuid() {
		return category_uuid;
	}
	
	public String getCategoryType() {
		return type;
	}
	
	public Boolean getCategoryIsDeleted() {
		return isDeleted;
	}
}
