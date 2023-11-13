package com.javaschool.onlineshop.models;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Shop_Users")
@Data
@NoArgsConstructor
public class ShopUserModel {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID userUuid;

    @Column(name = "user_name")
    private String name;
    
    @Column(name = "user_surname")
    private String surname;
    
    @Column(name = "user_birth")
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(name = "user_mail", unique = true)
    private String mail;
    
    @Column(name = "user_password")
    private String password;
    
    @Column(name ="user_isDeleted")
	private boolean isDeleted;
    
    //RELATIONS
    @OneToMany(mappedBy = "user", cascade= CascadeType.ALL)
	private List<UserAddressModel> address;
	
	@OneToMany(mappedBy = "shopUser", cascade= CascadeType.ALL)
	private List<OrderModel> orders;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="users_roles", joinColumns = @JoinColumn(name="user_uuid", referencedColumnName = "userUuid"),
        inverseJoinColumns = @JoinColumn(name = "role_uuid", referencedColumnName = "roleUuid"))
    private List<RoleModel> roles = new ArrayList<>();

}
