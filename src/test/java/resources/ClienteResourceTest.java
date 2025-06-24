package resources;

import org.junit.jupiter.api.*;
import io.quarkus.test.junit.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.db.Cliente;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

@QuarkusTest
public class ClienteResourceTest {
	
	private Map<String, Object> data = Map.of(
			"PrimerNombre", "Gabriel",
			"SegundoNombre", "Pedro",
			"PrimerApellido", "Peña",
			"SegundoApellido", "Lopez",
			"Correo", "gp@gmail.com",
			"Direccion", "Aqui",
			"Telefono", "829-659-0928",
			"Pais", "dom"
		 );
	
	private Cliente createCliente(Map<String, Object> payload, Integer statusCode) {
		Response response = given().contentType(ContentType.JSON)
    	.body(payload)
    	.when().post("/Client")
    	.then()
    		.log().body()
    		.statusCode(statusCode)
    		.extract().response();
		
		if (response.getStatusCode() < 400) {
			return response.getBody().as(Cliente.class);
		}
		return new Cliente();
	}

	@Test
	public void testCreate() {
		
		// Valido
	    this.createCliente(data, 201);
	    
	    // Invalido - Campos requeridos
		Map<String, Object> payload = new HashMap<>(data);
		payload.put("PrimerNombre", null);
		payload.put("SegundoNombre", null);
		payload.put("SegundoApellido", null);
		payload.put("Telefono", "829387564");
		payload.put("Pais", "d");
		
		this.createCliente(payload, 400);
		
		// Invalido - Pais equivocado
		payload = new HashMap<>(data);
		payload.put("Pais", "dup");

		this.createCliente(payload, 400);
	}
	
	@Test
	public void testGetAll() {
	    given()
	    	.when().get("/Client")
	    	.then()
    			.log().body()
	    		.statusCode(200);
	}
	
	@Test
	public void testGetById() {
		
		// Valido
		Cliente cliente = this.createCliente(data, 201);
		given()
			.when().get("/Client/" + cliente.Id)
			.then()
				.log().body()
				.statusCode(200);
		
		// Invalido - Id no existente
		given()
			.when().get("/Client/" + ++cliente.Id)
			.then()
				.log().body()
				.statusCode(404);
	}
	
	@Test
	public void testGetByCountry() {
		
		// Valido
		Cliente cliente = this.createCliente(data, 201);
		given()
			.when().get("/Client/Pais/" + cliente.Pais)
			.then()
				.log().body()
				.statusCode(200);
		
		// Invalido - codigo muy corto
		given()
			.when().get("/Client/Pais/" + 1)
			.then()
				.log().body()
				.statusCode(400);
	
		// Invalido - codigo muy largo
		given()
			.when().get("/Client/Pais/" + 1234)
			.then()
				.log().body()
				.statusCode(400);
	}
	
	@Test
	public void testUpdate() {
		
		// Valido
		Cliente cliente = this.createCliente(data, 201);
		Map<String, Object> payload = new HashMap<>(data);
		payload.put("PrimerNombre", "NombreNuevo");
		payload.put("Id", cliente.Id);
		
	    given()
	    	.body(payload).contentType(ContentType.JSON)
	    	.when().put("/Client")
	    	.then()
	    		.log().body()
	    		.statusCode(200);
	    
	    // 	Invalido - Id es requerido
	    payload = new HashMap<>(data);
	    payload.put("Id", null);
		
	    given()
	    	.body(payload).contentType(ContentType.JSON)
	    	.when().put("/Client")
	    	.then()
	    		.log().body()
	    		.statusCode(400);
	    
	    // Invalido - Id no es valido
	    payload = new HashMap<>(data);
	    payload.put("Id", ++cliente.Id);
		
	    given()
	    	.body(payload).contentType(ContentType.JSON)
	    	.when().put("/Client")
	    	.then()
	    		.log().body()
	    		.statusCode(404);
	    
	    // Invalido - Campos requeridos
	    payload = new HashMap<>(data);
		payload.put("PrimerNombre", null);
		payload.put("SegundoNombre", null);
		payload.put("SegundoApellido", null);
		payload.put("Telefono", "829-387-56412");
		payload.put("Pais", "dona");
		
	    given()
	    	.body(payload).contentType(ContentType.JSON)
	    	.when().put("/Client")
	    	.then()
	    		.log().body()
	    		.statusCode(400);
	}
	
	@Test
	public void testDelete() {
		
		// Valido
		Cliente cliente = this.createCliente(data, 201);
		given()
			.when().delete("/Client/" + cliente.Id)
		.	then()
				.log().body()
				.statusCode(200);
		
		// Invalido - Id es requerido
		given()
			.when().delete("/Client")
			.then()
				.log().body()
				.statusCode(405);
		
		// Invalido - Id no existe
		given()
			.when().delete("/Client/" + ++cliente.Id)
			.then()
				.log().body()
				.statusCode(404);
	}
}