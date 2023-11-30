package com.javaschool.onlineshop.models;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Products")
@Data
@NoArgsConstructor
public class ProductsModel {
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID productUuid;
	
	@OneToOne
    @JoinColumn(name = "product_category_uuid")
    private CategoryModel category;
	
	@ManyToOne
	@JoinColumn(name = "product_platform_uuid")
	private PlatformsModel platform;

	@Column(name = "product_title")
	private String title;
	
	@Column(name = "product_price")
	private Double price;

	@Column(name = "product_stock")
	private Integer stock;
	
	@Column(name = "product_PEGI")
	private Integer PEGI;
	
	@Column(name = "product_isDigital")
	private Boolean isDigital;
	
	@Column(name = "product_description")
	private String description;
	
	@Column(name = "product_image")
	private String image;
				
	@Column(name = "product_isDeleted")
	private Boolean isDeleted;
	
	//RELATIONS
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="product_genres", joinColumns = @JoinColumn(name="product_uuid", referencedColumnName = "productUuid"),
			inverseJoinColumns = @JoinColumn(name = "genre_uuid", referencedColumnName = "genreUuid"))
	private List<GenreModel> genres = new ArrayList<>();
	
	@OneToMany(mappedBy = "product", cascade= CascadeType.ALL)
	private List<OrderProductsModel> product_orders;

}
