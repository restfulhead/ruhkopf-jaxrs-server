package name.ruhkopf.jaxrs.server.service;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class EncryptionServiceImplTest extends AbstractServiceTest
{
	private static Logger LOG = LogManager.getLogger();
	
	@Inject
	private EncryptionService encryptionService;

	@Test
	public void shouldCreateHash()
	{
		assertThat(encryptionService.createHash("password"), not(isEmptyString()));
		assertThat(encryptionService.createHash("some password"), not(isEmptyString()));
		assertThat(encryptionService.createHash("s0m!PW%$$&~@#rt"), not(isEmptyString()));
	}

	@Test
	public void shouldValidateHash()
	{
		for(int i = 0; i < 100; i++)
      {
          String password = "s0m!PW%$$&~@#rt" + i;
          String hash = encryptionService.createHash(password);
          String secondHash = encryptionService.createHash(password);
          
          // the two hashes should not be the same
          LOG.trace("Comparing '{}' and '{}' for '{}", hash, secondHash, password);
          assertNotEquals(hash, secondHash);
          
          // password should validate against both hashes
          assertTrue(encryptionService.validate(password, hash));
          assertTrue(encryptionService.validate(password, secondHash));
          
          // a wrong password should not validate
          assertFalse(encryptionService.validate(password + i, hash));
          assertFalse(encryptionService.validate(password + i, secondHash));
      }
	}
}
