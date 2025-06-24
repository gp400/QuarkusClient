package models.db;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Error {
	
	@Id
	@GeneratedValue
	public Long Id;
	
	@NotNull
	public String Mensaje;
}