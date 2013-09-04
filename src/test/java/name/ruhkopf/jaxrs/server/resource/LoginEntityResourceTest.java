package name.ruhkopf.jaxrs.server.resource;

import static org.junit.Assert.assertNotNull;
import name.ruhkopf.jaxrs.server.model.LoginEntity;

import org.junit.Test;

public class LoginEntityResourceTest extends AbstractResourceTest
{
	@Test
	public void shouldReturnVersion()
	{
		final LoginEntity result = target("logins").request().get(LoginEntity.class);
		assertNotNull(result);
	}

	@Override
	protected Class<?>[] getResources()
	{
		return new Class[] { LoginEntityResource.class };
	}
}
