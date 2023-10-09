package com.javaschool.onlineshop.Models;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Products_has_Genre")
@Data
@NoArgsConstructor
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
}
