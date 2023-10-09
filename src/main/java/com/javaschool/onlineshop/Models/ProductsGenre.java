package com.javaschool.onlineshop.Models;
import jakarta.persistence.*;

@Entity
@Table(name="Products_has_Genre")
public class ProductsGenre {

	//COLUMNS
	@Id
	@ManyToOne
	@JoinColumn(name = "product_uuid")
	private Products product;
		
	@Id
	@ManyToOne
	@JoinColumn(name = "genre_uuid")
	private Genre genre;
	
	//GETTERS AND SETTERS
	public Products getProducts() {
		return product;
	}
	
	public Genre getGenre() {
		return genre;
	}
}
