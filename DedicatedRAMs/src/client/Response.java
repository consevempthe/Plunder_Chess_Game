package client;

/**
 * Response is a simple interface requiring classes that implement it to Override the method, handleResponse(). Helps avoid code duplication when creating a general Response that the specifics haven't been determined.
 * @author DedicatedRAMs NF
 *
 */
public interface Response {
	void handleResponse();
}
