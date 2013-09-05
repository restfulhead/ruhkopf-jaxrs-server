package name.ruhkopf.jaxrs.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import name.ruhkopf.jaxrs.server.model.LoginEntity;
import name.ruhkopf.jaxrs.server.service.PersistenceService.Closure;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class UsernamePasswordLoginServiceIntegrationTest extends AbstractServiceTest
{
	@Inject
	private LoginServiceProvider usernamePasswordLoginService;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Inject
	private PersistenceService persistenceService;

	@Before
	public void checkDatabaseState()
	{
		assertThat(usernamePasswordLoginService.findAllLogins(0, 10), empty());
		assertThat(usernamePasswordLoginService.countAllLogins(), is(0L));
	}

	/**
	 * since this test is not transactional on purpose, stuff actually gets written to the db. let's be nice and cleanup
	 * after
	 */
	@After
	public void cleanup()
	{
		persistenceService.runInTransaction(new Closure()
		{
			@Override
			public void invoke(EntityManager em)
			{
				for (LoginEntity login : usernamePasswordLoginService.findAllLogins(0, 10))
				{
					em.remove(login);
				}
			}
		});

	}

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
