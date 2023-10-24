package com.javaschool.onlineshop.models;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;


@Entity
@Table(name="User_Address")
@Data
@NoArgsConstructor
public class UserAddress {

	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID addressUuid;
	
	@ManyToOne
	@JoinColumn(name = "address_user_uuid")
	private ShopUser user;
	
	@OneToOne
	@JoinColumn(name = "address_postal_code_uuid")
	private PostalCode postal_code;
	
	@Column(name = "address_street")
	private String street;
	
	@Column(name = "address_home")
	private String home;
	
	@Column(name = "address_apartament")
	private String apartament;
	
	@Column(name = "address_isDeleted")
	private Boolean isDeleted;
	
}
