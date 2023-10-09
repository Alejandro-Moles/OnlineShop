package com.javaschool.onlineshop.Models;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="Platforms")
public class Platforms {
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID platform_uuid;

	@Column(name = "platform_type")
	private String type;
		
	@Column(name = "platform_isDeleted")
	private Boolean isDeleted;
	
	//RELATIONS
	@OneToMany(mappedBy = "platform", cascade= CascadeType.ALL)
	private List<Products> products;
	
	//GETTERS AND SETTERS
	public UUID GetPlatformUuid() {
		return platform_uuid;
	}
		
	public String GetPlatformType() {
		return type;
	}
		
	public Boolean GetPlatformIsDeleted() {
		return isDeleted;
	}
	
	public List<Products> GetProductsPlatform(){
		return products;
	}
		
}
