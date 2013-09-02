package name.ruhkopf.jaxrs.server.resource;

import org.glassfish.jersey.server.ResourceConfig;


public class AppResourceConfig extends ResourceConfig
{
	public AppResourceConfig()
	{
		packages(this.getClass().getPackage().toString());
	}

}
