package providers;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.*;

@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException exception) {
    List<FieldError> payload = new ArrayList<FieldError>();
    exception.getConstraintViolations().stream()
    		.forEach(v -> {
    			String[] propertyArray = v.getPropertyPath().toString().split("[.]");
    			payload.add(new FieldError(propertyArray[ propertyArray.length - 1 ], v.getMessage()));
    		});
        
    return Response.status(Response.Status.BAD_REQUEST)
                   .entity(payload)
                   .build();
	}

    public static record FieldError(String field, String message) {}
}