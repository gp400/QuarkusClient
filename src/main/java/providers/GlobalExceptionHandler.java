package providers;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.*;
import services.ErrorService;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {
	
	@Inject
	ErrorService errorService;

	@Override
	public Response toResponse(Throwable exception) {
		
		String message = exception.getMessage();
		errorService.saveLog(message);
		
		if (exception instanceof NotAllowedException) {
			return Response.status(Response.Status.METHOD_NOT_ALLOWED)
						.entity(message)
						.build();
		} else if (exception instanceof NotFoundException) {
			return Response.status(Response.Status.NOT_FOUND)
						.entity(message)
						.build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(message)
					.build();
		}
	}

}