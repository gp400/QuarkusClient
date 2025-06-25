package resources;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import models.db.Cliente;
import models.dto.ClienteDto;
import models.dto.validationGroups.ClienteValidationGroup;
import services.ClienteService;

@Path("Client")
public class ClienteResource {
	
	@Inject
	ClienteService clienteService;
	
	@GET
	public Response getAll() {
		List<Cliente> clientes = clienteService.getClients();
		return Response.ok(clientes).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") Long id) throws Exception {
		Cliente cliente = clienteService.getClientById(id);
		return Response.ok(cliente).build();
	}
	
	@GET
	@Path("/Pais/{pais}")
	public Response getByCountry(@PathParam("pais")
							@Size(min = 2, max = 3, message = "El codigo del pais no es valido")
							String pais) {
		List<Cliente> clientes = clienteService.getClientsByCountry(pais);
		return Response.ok(clientes).build();
	}
	
	@POST
	public Response create(@Valid 
							@ConvertGroup(to = ClienteValidationGroup.Create.class) 
							ClienteDto clienteDto) throws Exception {
		Cliente cliente = clienteService.createClient(clienteDto);
		return Response
					.status(Response.Status.CREATED)
					.entity(cliente)
					.build();
	}
	
	@PUT
	public Response update(@Valid
							@ConvertGroup(to = ClienteValidationGroup.Update.class) 
							ClienteDto clienteDto) throws Exception {
		Cliente cliente = clienteService.updateClient(clienteDto);
		return Response.ok(cliente).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) throws Exception {
		clienteService.deleteClient(id);
		return Response.ok().build();
	}
}