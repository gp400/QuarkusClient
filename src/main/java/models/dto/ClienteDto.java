package models.dto;

import jakarta.validation.constraints.*;
import models.dto.validationGroups.ClienteValidationGroup;

public class ClienteDto {

	@NotNull(groups = ClienteValidationGroup.Update.class,
			message = "El Id del cliente es requerido")
	public Long Id;
	
	@NotBlank(groups = ClienteValidationGroup.Create.class,
			message = "El Primer Nombre es requerido")
	public String PrimerNombre;
	
	public String SegundoNombre;
	
	@NotBlank(groups = ClienteValidationGroup.Create.class,
			message = "El Primer Apellido es requerido")
	public String PrimerApellido;
	
	public String SegundoApellido;
	
	@NotBlank(message = "El Correo es requerido")
	@Email(message = "El Correo no es valido")
	public String Correo;
	
	@NotBlank(message = "La Direccion es requerida")
	public String Direccion;
	
	@NotBlank(message = "El Telefono es requerido")
	@Size(min = 10, max = 12, message = "El telefono no	 es valido") // Por si incluye o no guiones
	public String Telefono;
	
	@NotBlank(message = "El Codigo es requerido")
	@Size(min = 2, max = 3, message = "El Codigo no es valido")
	public String Pais;
}