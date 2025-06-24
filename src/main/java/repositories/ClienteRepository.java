package repositories;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import models.db.Cliente;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
	
	public List<Cliente> findByCountry(String country) {
		return list("Pais = ?1", country);
	}
	
}