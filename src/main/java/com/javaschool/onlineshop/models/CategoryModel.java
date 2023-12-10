package com.javaschool.onlineshop.models;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

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

	@OneToMany(mappedBy = "category", cascade= CascadeType.ALL)
	private List<ProductsModel> products;
}
