package com.javaschool.onlineshop.Models;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Platforms")
@Data
@NoArgsConstructor
public class Platforms {
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID platform_uuid;

	@Column(name = "platform_type")
	private String type;
		
	@Column(name = "platform_isDeleted")
	private Boolean isDeleted;
	
	//RELATIONS
	@OneToMany(mappedBy = "platform", cascade= CascadeType.ALL)
	private List<Products> products;	
}
