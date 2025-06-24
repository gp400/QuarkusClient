package services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import repositories.ErrorRepository;
import models.db.Error;

@ApplicationScoped
public class ErrorService {

    @Inject
    ErrorRepository errorRepository;
	
    @Transactional
    public void saveLog(String message) {
    	Error error = new Error();
    	error.Mensaje = message;
    	
    	errorRepository.persist(error);
    }
}
