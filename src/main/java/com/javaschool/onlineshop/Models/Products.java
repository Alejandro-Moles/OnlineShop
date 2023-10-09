package com.javaschool.onlineshop.Models;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="Products")
public class Products {
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID product_uuid;
	
	@OneToOne
    @JoinColumn(name = "product_category_uuid")
    private Category categoty;
	
	@ManyToOne
	@JoinColumn(name = "product_platform_uuid")
	private Platforms platform;

	@Column(name = "product_title")
	private String title;
	
	@Column(name = "product_price")
	private Double price;
	
	@Column(name = "product_weight")
	private Double weight;
	
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
	@OneToMany(mappedBy = "product", cascade= CascadeType.ALL)
	private List<ProductsGenre> products_genre;
	
	@OneToMany(mappedBy = "ProductOrders", cascade= CascadeType.ALL)
	private List<OrderProducts> product_orders;

	//GETTERS AND SETTERS
	public UUID getProduct_uuid() {
		return product_uuid;
	}

	public Category getProductsCategoty() {
		return categoty;
	}

	public Platforms getProductsPlatform() {
		return platform;
	}

	public String getProductsTitle() {
		return title;
	}

	public Double getProductsPrice() {
		return price;
	}

	public Double getProductsWeight() {
		return weight;
	}

	public Integer getProductsStock() {
		return stock;
	}

	public Integer getProductsPEGI() {
		return PEGI;
	}

	public Boolean getProductsIsDigital() {
		return isDigital;
	}

	public void setIsDigital(Boolean isDigital) {
		this.isDigital = isDigital;
	}

	public String getProductsDescription() {
		return description;
	}

	public String getProductsImage() {
		return image;
	}

	public Boolean getProductsIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<ProductsGenre> getProducts_genre() {
		return products_genre;
	}

	public List<OrderProducts> getProduct_orders() {
		return product_orders;
	}
	
	
}
