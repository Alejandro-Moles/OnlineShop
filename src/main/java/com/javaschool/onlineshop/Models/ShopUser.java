package com.javaschool.onlineshop.Models;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="Shop_Users")
public class ShopUser {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID user_uuid;
	
    @OneToOne
    @JoinColumn(name = "user_role_uuid")
    private Role users_rol;

    @Column(name = "user_name")
    private String name;
    
    @Column(name = "user_surname")
    private String surname;
    
    @Column(name = "user_birth")
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(name = "user_mail")
    private String mail;
    
    @Column(name = "user_password")
    private String password;
    
    @Column(name ="user_isDeleted")
	private boolean isDeleted;
    
    //RELATIONS
    @OneToMany(mappedBy = "user", cascade= CascadeType.ALL)
	private List<User_Address> address;
	
	@OneToMany(mappedBy = "shopUser", cascade= CascadeType.ALL)
	private List<Order> orders;
    
	//GETTERS AND SETTERS
    public UUID getuser_uuid() {
		return user_uuid;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getSurName() {
    	return surname;
    }
    
    public String getMail() {
    	return mail;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public Date getDate(){
    	return date;
    }
    
    public Boolean getIsDeleted() {
		return isDeleted;
	}
    
    public List<User_Address> getUserAddress(){
    	return address;
    }
    
    public Role getUserRole() {
    	return users_rol;
    }

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
