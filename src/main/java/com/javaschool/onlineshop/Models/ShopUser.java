package com.javaschool.onlineshop.Models;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Shop_Users")
@Data
@NoArgsConstructor
public class ShopUser {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
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
	private List<UserAddress> address;
	
	@OneToMany(mappedBy = "shopUser", cascade= CascadeType.ALL)
	private List<Order> orders;
    
}
