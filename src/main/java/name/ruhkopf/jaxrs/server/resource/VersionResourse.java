package name.ruhkopf.jaxrs.server.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import name.ruhkopf.jaxrs.server.service.ConfigurationService;

@Path("/version")
public class VersionResourse 
{
	@Inject
	private ConfigurationService configurationService;

	@GET
	@Produces ("text/plain")
	public String get()
	{
		return configurationService.get("application.version");
	}
	
}
