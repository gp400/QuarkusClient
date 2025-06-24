package models.db;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue
	public Long Id;
	
	@NotNull
	public String PrimerNombre;
	
	public String SegundoNombre;
	
	@NotNull
	public String PrimerApellido;
	
	public String SegundoApellido;
	
	@NotNull
	public String Correo;
	
	@NotNull
	public String Direccion;
	
	@NotNull
	@Column(length = 15)
	public String Telefono;
	
	@NotNull
	@Column(length = 3)
	public String Pais;
	
	@NotNull
	public String Gentilicio;
}
