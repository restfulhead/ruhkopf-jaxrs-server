package name.ruhkopf.jaxrs.server.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import name.ruhkopf.jaxrs.server.model.LoginEntity;

@Path("/logins")
public class LoginEntityResource
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public LoginEntity get()
	{
		return new LoginEntity("test", "test", null);
	}
}
