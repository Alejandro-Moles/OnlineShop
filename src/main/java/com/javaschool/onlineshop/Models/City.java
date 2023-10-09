package com.javaschool.onlineshop.Models;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="Cities")
public class City {
	
	//COLUMNS
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID city_uuid; 
	
	@ManyToOne
	@JoinColumn(name = "city_country_uuid")
	private Country country;
	
	@Column(name = "city_name")
	private String name;
	
	@Column(name ="city_isDeleted")
	private boolean isDeleted;
	
	//RELATIONS
	@OneToMany(mappedBy = "city_uuid", cascade= CascadeType.ALL)
	private List<PostalCode> postal_codes;
	
	//GETTERS AND SETTERS
	public UUID getCountryUuid() {
		return city_uuid;
	}
	
	public String getCityName() {
		return name;
	}
	
	public Boolean getCityIsDeleted() {
		return isDeleted;
	}
	
	public Country getCityCountry() {
		return country;
	}
	
	public List<PostalCode> getCityPostalCodes(){
		return postal_codes;
	}
}
