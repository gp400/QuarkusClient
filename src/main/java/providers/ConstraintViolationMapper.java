package providers;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.json.bind.*;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.*;
import services.ErrorService;

@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

	@Inject
	ErrorService errorService;
	
	@Override
	public Response toResponse(ConstraintViolationException exception) {
    List<FieldError> payload = new ArrayList<FieldError>();
    exception.getConstraintViolations().stream()
    		.forEach(v -> {
    			String[] propertyArray = v.getPropertyPath().toString().split("[.]");
    			payload.add(new FieldError(propertyArray[ propertyArray.length - 1 ], v.getMessage()));
    		});
    
    Jsonb jsonBuilder = JsonbBuilder.create();
    String jsonPayload = jsonBuilder.toJson(payload);
    errorService.saveLog(jsonPayload);
        
    return Response.status(Response.Status.BAD_REQUEST)
                   .entity(payload)
                   .build();
	}

    public static record FieldError(String field, String message) {}
}