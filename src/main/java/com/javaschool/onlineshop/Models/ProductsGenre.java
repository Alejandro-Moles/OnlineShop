package com.javaschool.onlineshop.Models;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Table(name="Products_has_Genre")
@Data
@NoArgsConstructor
public class ProductsGenre {

	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID products_genre_uuid;

	@ManyToOne
	@JoinColumn(name = "product_uuid")
	private Products product;

	@ManyToOne
	@JoinColumn(name = "genre_uuid")
	private Genre genre;

	@Column(name = "isDeleted")
	private Boolean isDeleted;
}
