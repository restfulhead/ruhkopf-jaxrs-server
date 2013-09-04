package name.ruhkopf.jaxrs.server.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import name.ruhkopf.jaxrs.server.model.LoginEntity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UsernamePasswordLoginServiceTest extends AbstractServiceTest
{
	@Inject
	private LoginServiceProvider usernamePasswordLoginService;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	private static final String TEST_USER_TOKEN = "testuser";

	@Before
	public void checkDatabaseState()
	{
		assertThat(usernamePasswordLoginService.findAllLogins(0, 10), empty());
		assertThat(usernamePasswordLoginService.countAllLogins(), is(0L));
	}
	
	@Test
	public void shouldCreateAndFindUser()
	{
		LoginEntity newLogin = usernamePasswordLoginService.createLogin(TEST_USER_TOKEN, "testpassword");
		assertNotNull(newLogin.getId());
		
		LoginEntity existingUser = usernamePasswordLoginService.findLogin(newLogin.getId());
		assertNotNull(existingUser);
		assertThat(existingUser.getUserToken(), equalTo(TEST_USER_TOKEN));
		assertEquals(newLogin, existingUser);
		
		assertThat(usernamePasswordLoginService.findAllLogins(0, 10), not(empty()));
		assertThat(usernamePasswordLoginService.countAllLogins(), is(1L));
	}
	
	@Test
	public void shouldNotCreateEmptyUserToken()
	{
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("userToken");
		usernamePasswordLoginService.createLogin("   ", "123");
	}
	
	@Test
	public void shouldNotCreateNullAccessToken()
	{
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("accessToken");
		usernamePasswordLoginService.createLogin("test", null);
	}
}
