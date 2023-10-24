package com.javaschool.onlineshop.models;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Genre")
@Data
@NoArgsConstructor
public class Genre {
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID genreUuid;

	@Column(name = "genre_type", unique = true)
	private String type;
			
	@Column(name = "genre_isDeleted")
	private Boolean isDeleted;
	
	//RELATIONS
	@OneToMany(mappedBy = "genre", cascade= CascadeType.ALL)
	private List<ProductsGenre> products_genre;
}
