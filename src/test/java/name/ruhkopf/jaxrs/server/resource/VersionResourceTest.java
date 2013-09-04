package name.ruhkopf.jaxrs.server.resource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import name.ruhkopf.jaxrs.server.service.ConfigurationServiceImpl;

import org.junit.Test;


public class VersionResourceTest extends AbstractResourceTest
{
	@Test
	public void shouldReturnVersion()
	{
		final String result = target("version").request().get(String.class);
		String version = new ConfigurationServiceImpl().get("application.version");
		assertThat(result, equalTo(version));
	}
	
	@Override
	protected Class<?>[] getResources()
	{
		return new Class[] { VersionResourse.class };
	}
}
