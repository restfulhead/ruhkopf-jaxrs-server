package name.ruhkopf.jaxrs.server.resource;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

public abstract class AbstractResourceTest extends JerseyTest
{
	protected abstract Class<?>[] getResources();
	
	@Override
	protected Application configure()
	{
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		
		return new ResourceConfig(getResources());
	}
}
