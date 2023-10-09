package com.javaschool.onlineshop.Models;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="Genre")
public class Genre {
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID genre_uuid;

	@Column(name = "genre_type")
	private String type;
			
	@Column(name = "genre_isDeleted")
	private Boolean isDeleted;
	
	//RELATIONS
	@OneToMany(mappedBy = "genre", cascade= CascadeType.ALL)
	private List<ProductsGenre> products_genre;
		
	//GETTERS AND SETTERS
	public UUID getGenreUuid() {
		return genre_uuid;
	}
			
	public String getGenreType() {
		return type;
	}
			
	public Boolean getGenreIsDeleted() {
		return isDeleted;
	}
	
	public List<ProductsGenre> getProductsGenre(){
		return products_genre;
	}
}
