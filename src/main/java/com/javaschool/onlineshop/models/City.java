package com.javaschool.onlineshop.models;
import java.util.List;
import lombok.Data;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Cities")
@Data
@NoArgsConstructor
public class City {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID city_uuid; 

	@ManyToOne
	@JoinColumn(name = "city_country_uuid")
	private Country country;
	
	@Column(name = "city_name")
	private String name;
	
	@Column(name ="city_isDeleted")
	private boolean isDeleted;
	
	//RELATIONS
	@OneToMany(mappedBy = "city", cascade= CascadeType.ALL)
	private List<PostalCode> postal_codes;
}
