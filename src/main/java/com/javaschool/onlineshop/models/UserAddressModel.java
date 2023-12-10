package com.javaschool.onlineshop.models;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="User_Address")
@Data
@NoArgsConstructor
public class UserAddressModel {

	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID addressUuid;
	
	@ManyToOne
	@JoinColumn(name = "address_user_uuid")
	private ShopUserModel user;
	
	@ManyToOne
	@JoinColumn(name = "address_postal_code_uuid")
	private PostalCodeModel postal_code;
	
	@Column(name = "address_street")
	private String street;
	
	@Column(name = "address_home")
	private String home;
	
	@Column(name = "address_apartament")
	private String apartament;
	
	@Column(name = "address_isDeleted")
	private Boolean isDeleted;

	@OneToMany(mappedBy = "userAddress", cascade= CascadeType.ALL)
	private List<OrderModel> orders;
	
}
