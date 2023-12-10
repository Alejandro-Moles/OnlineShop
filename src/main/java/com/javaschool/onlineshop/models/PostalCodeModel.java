package com.javaschool.onlineshop.models;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Postal_Code")
@Data
@NoArgsConstructor
public class PostalCodeModel {

	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID postalCodeUuid;
	
	@ManyToOne
    @JoinColumn(name = "postal_code_city_uuid")
    private CityModel city;
	
	@Column(name = "postal_code_content",unique = true)
	private String content;
	
	@Column(name ="postal_code_isDeleted")
	private boolean isDeleted;

	@OneToMany(mappedBy = "postal_code", cascade= CascadeType.ALL)
	private List<UserAddressModel> address;
}
