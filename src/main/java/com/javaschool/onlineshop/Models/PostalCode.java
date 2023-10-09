package com.javaschool.onlineshop.Models;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="Postal_Code")
public class PostalCode {

	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID postal_code_uuid;
	
	@ManyToOne
    @JoinColumn(name = "postal_code_city_uuid")
    private City city_uuid;
	
	@Column(name = "postal_code_content")
	private String content;
	
	@Column(name ="postal_code_isDeleted")
	private boolean isDeleted;
	
	//GETTERS AND SETTERS
	public UUID getPostalCodeUuid() {
		return postal_code_uuid;
	}
	
	public City getCityCountry() {
		return city_uuid;
	}
	
	public String getPostalCodeContent() {
		return content;
	}
	
	public Boolean getPostalCodeIsDeleted() {
		return isDeleted;
	}
	
}
