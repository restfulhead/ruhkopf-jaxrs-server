package name.ruhkopf.jaxrs.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import name.ruhkopf.jaxrs.server.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UserServiceTest
{
	@Inject
	private UserServiceImpl userService;

	@Test
	public void shouldCreateAndFindUser()
	{
		User newUser = new User();
		newUser.setName("testuser");
		userService.createNewUser(newUser);
		assertNotNull(newUser.getId());
		
		User existingUser = userService.findUser(newUser.getId());
		assertNotNull(existingUser);
		
		assertEquals(newUser, existingUser);
	}
}
