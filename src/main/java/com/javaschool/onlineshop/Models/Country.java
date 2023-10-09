package com.javaschool.onlineshop.Models;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="Countries")
public class Country {
	//COLUMNS
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID country_uuid; 
	
	@Column(name = "country_name")
	private String name;
	
	@Column(name ="country_isDeleted")
	private boolean isDeleted;
	
	//RELATION
	@OneToMany(mappedBy = "country", cascade= CascadeType.ALL)
	private List<City> cities;
	
	//GETTERS AND SETTERS
	public UUID getCountryUuid() {
		return country_uuid;
	}
	
	public String getCountryName() {
		return name;
	}
	
	public Boolean getCountryIsDeleted() {
		return isDeleted;
	}
	
	public List<City> getCities(){
		return cities;
	}
}
