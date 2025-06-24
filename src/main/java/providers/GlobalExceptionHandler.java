package providers;

import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.*;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		
		if (exception instanceof NotAllowedException) {
			return Response.status(Response.Status.METHOD_NOT_ALLOWED)
						.entity(exception.getMessage())
						.build();
		} else if (exception instanceof NotFoundException) {
			return Response.status(Response.Status.NOT_FOUND)
						.entity(exception.getMessage())
						.build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(exception.getMessage())
					.build();
		}
	}

}