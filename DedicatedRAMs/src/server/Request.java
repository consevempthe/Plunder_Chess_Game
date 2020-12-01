package server;

/**
 * Request is a simple interface requiring classes that implement it to Override the method, buildResponse(). Helps avoid code duplication when creating a general Request that the specifics haven't been determined.
 * @author DedicatedRAMs NF
 *
 */
public interface Request {
	String buildResponse();
}
