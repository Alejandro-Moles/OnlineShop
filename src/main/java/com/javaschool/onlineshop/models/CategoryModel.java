package com.javaschool.onlineshop.models;
import java.util.UUID;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
@Table(name="Category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryModel {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID categoryUuid;

	@Column(name = "category_type",  unique = true)
	private String type;
	
	@Column(name = "category_isDeleted")
	private Boolean isDeleted;
}
