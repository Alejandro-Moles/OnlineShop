package com.javaschool.onlineshop.Models;
import java.util.UUID;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Category")
@Data
@NoArgsConstructor
public class Category {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID category_uuid;

	@Column(name = "category_type")
	private String type;
	
	@Column(name = "category_isDeleted")
	private Boolean isDeleted;
}
