package services;

import java.net.http.*;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.ClientHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import models.db.Cliente;
import models.dto.ClienteDto;
import repositories.ClienteRepository;

@ApplicationScoped
public class ClienteService {
    
    @Inject
    ClienteRepository clienteRepository;
    
    public List<Cliente> getClients(){
    	return clienteRepository.listAll();
    }
    
    public Cliente getClientById(Long id) throws NotFoundException {
    	Cliente cliente = clienteRepository.findById(id);
    	if (cliente == null) throw new NotFoundException("No existe un cliente con ese Id");
    	return cliente;
    }
    
    public List<Cliente> getClientsByCountry(String country){
    	return clienteRepository.findByCountry(country);
    }
	
	@Transactional
	public Cliente createClient(ClienteDto clienteDto) throws Exception {
		Cliente cliente = new Cliente();
		
		String gentilicio = this.getGentilicio(clienteDto.Pais);
		cliente.PrimerNombre = clienteDto.PrimerNombre;
		cliente.SegundoNombre = clienteDto.SegundoNombre;
		cliente.PrimerApellido = clienteDto.PrimerApellido;
		cliente.SegundoApellido = clienteDto.SegundoApellido;
		cliente.Correo = clienteDto.Correo;
		cliente.Direccion = clienteDto.Direccion;
		cliente.Telefono = clienteDto.Telefono;
		cliente.Pais = clienteDto.Pais;
		cliente.Gentilicio = gentilicio;
		
		clienteRepository.persist(cliente);
		
		return cliente;
	}
	
	@Transactional
	public Cliente updateClient(ClienteDto clienteDto) throws Exception {
		Cliente cliente = this.getClientById(clienteDto.Id);
		
		if (cliente == null) throw new Exception("No existe un cliente con ese Id");

		String gentilicio = this.getGentilicio(clienteDto.Pais);
		cliente.Correo = clienteDto.Correo;
		cliente.Direccion = clienteDto.Direccion;
		cliente.Telefono = clienteDto.Telefono;
		cliente.Pais = clienteDto.Pais;
		cliente.Gentilicio = gentilicio;
		
		return cliente;
	}
	
	@Transactional
	public void deleteClient(Long id) throws Exception {
		Boolean deleted = clienteRepository.deleteById(id);
		
		if (!deleted) throw new NotFoundException("No existe un cliente con ese Id");
	}
	
	private String getGentilicio(String ISOCode) throws Exception {
		HttpResponse<String> response = ClientHelper.GET("https://restcountries.com/v3.1/alpha/"+ISOCode);
		if (response.statusCode() >= 400) {
			throw new Exception("El codigo del pais no es valido");
		} else {
    	    ObjectMapper mapper = new ObjectMapper();
    	    JsonNode country = mapper.readTree(response.body());    	    
    	    String gentilicio = country.get(0).get("demonyms").get("eng").get("m").textValue();
			return gentilicio;
		}
	}
}