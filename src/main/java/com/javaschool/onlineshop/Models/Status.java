package com.javaschool.onlineshop.Models;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="Order_Status")
public class Status {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID status_uuid;
		
	@Column(name = "status_type")
	private String type;
		
	@Column(name = "status_isDeleted")
	private Boolean isDeleted;

		
	//GETTERS AND SETTERS
	public UUID getStatustUuid() {
		return status_uuid;
	}

	public String getStatusType() {
		return type;
	}

	public Boolean getStatusIsDeleted() {
		return isDeleted;
	}
}
