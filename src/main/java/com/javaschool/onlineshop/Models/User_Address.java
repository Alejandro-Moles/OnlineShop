package com.javaschool.onlineshop.Models;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="User_Address")
public class User_Address {

	//COLUMNS
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID address_uuid;
	
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
	
	
	//GETTERS AND SETTERS
	public UUID getAddUuid() {
		return address_uuid;
	}
	
	public ShopUser getAddShopUser() {
		return user;
	}
	
	public PostalCode getAddPostalCode() {
		return postal_code;
	}
	
	public String getAddStreet() {
		return street;
	}
	
	public String getAddHome() {
		return home;
	}
	
	public String getAddApartament() {
		return apartament;
	}
	
	public Boolean getAddIsDeleted() {
		return isDeleted;
	}
}
