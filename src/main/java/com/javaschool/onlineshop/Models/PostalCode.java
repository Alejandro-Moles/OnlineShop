package com.javaschool.onlineshop.Models;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Postal_Code")
@Data
@NoArgsConstructor
public class PostalCode {

	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID postal_code_uuid;
	
	@ManyToOne
    @JoinColumn(name = "postal_code_city_uuid")
    private City city;
	
	@Column(name = "postal_code_content",unique = true)
	private String content;
	
	@Column(name ="postal_code_isDeleted")
	private boolean isDeleted;

	
}
