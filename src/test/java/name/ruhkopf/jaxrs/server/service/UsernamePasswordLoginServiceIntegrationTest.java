package name.ruhkopf.jaxrs.server.service;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import javax.inject.Inject;

import name.ruhkopf.jaxrs.server.model.LoginEntity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class UsernamePasswordLoginServiceIntegrationTest extends AbstractServiceTest
{
	@Inject
	private LoginServiceProvider usernamePasswordLoginService;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void shouldNotCreateDuplicateUserToken()
	{
		final String TEST_USER_TOKEN = UUID.randomUUID().toString();
		LoginEntity newLogin = usernamePasswordLoginService.createLogin(TEST_USER_TOKEN, "testpassword");
		assertNotNull(newLogin.getId());

		expectedException.expect(LoginAlreadyExistsException.class);
		expectedException.expectMessage(LoginAlreadyExistsException.CODE);
		usernamePasswordLoginService.createLogin(TEST_USER_TOKEN, "testpassword");
	}

}
