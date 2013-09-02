package name.ruhkopf.jaxrs.server.resource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Application;

import name.ruhkopf.jaxrs.server.service.ConfigurationServiceImpl;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;


public class VersionResourceTest extends JerseyTest
{

	@Override
	protected Application configure()
	{
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		
		return new ResourceConfig(VersionResourse.class);
	}
	
	@Test
	public void shouldTest()
	{
		final String result = target("version").request().get(String.class);
		String version = new ConfigurationServiceImpl().get("application.version");
		assertThat(result, equalTo(version));
	}
}
