package name.ruhkopf.jaxrs.server.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import name.ruhkopf.jaxrs.server.model.LoginEntity;
import name.ruhkopf.jaxrs.server.service.PersistenceService.Closure;
import name.ruhkopf.jaxrs.server.util.Precondition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;


@Component("usernamePasswordLoginService")
public class UsernamePasswordLoginService implements LoginServiceProvider
{
	private static Logger LOG = LogManager.getLogger();
	
	protected final static String ACCESS_TYPE = "password";
	
	@Inject
	private PersistenceService persistenceService;

	@Inject
	private EncryptionService encryptionService;

	/* (non-Javadoc)
	 * @see name.ruhkopf.jaxrs.server.service.LoginService#createLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public LoginEntity createLogin(String userToken, String accessToken)
	{
		Precondition.checkNotBlank(userToken, "userToken");
		Precondition.checkNotBlank(accessToken, "accessToken");

		String encryptedToken = encryptionService.createHash(accessToken);
		
		final LoginEntity login = new LoginEntity(userToken, encryptedToken, ACCESS_TYPE);
		LOG.info("Persisting login {} (type='{}')", login.getUserToken(), ACCESS_TYPE);

		try
		{
			persistenceService.runInTransaction(new Closure()
			{
				@Override
				public void invoke(EntityManager em)
				{
					em.persist(login);
				}
			});
		}
		catch (DataIntegrityViolationException ex)
		{
			// user already exists
			LOG.info("Persisting login {} (type='{}') failed due to {}", login.getUserToken(), ACCESS_TYPE, ex.getMessage());
			throw new LoginAlreadyExistsException(login.getUserToken());
		}

		return login;
	}
	

	/* (non-Javadoc)
	 * @see name.ruhkopf.jaxrs.server.service.LoginService#findAllLogins(int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoginEntity> findAllLogins(int start, int count)
	{
		return persistenceService.getEm().createQuery("SELECT l FROM LoginEntity l WHERE l.accessType = :accessType")
			    .setParameter("accessType", ACCESS_TYPE)
			    .setFirstResult(start)
			    .setMaxResults(count)
			    .getResultList();
	}
	
	/* (non-Javadoc)
	 * @see name.ruhkopf.jaxrs.server.service.LoginService#countAllLogins()
	 */
	@Override
	public long countAllLogins()
	{
		return (long) persistenceService.getEm().createQuery("SELECT count(l) FROM LoginEntity l WHERE l.accessType = :accessType")
			    .setParameter("accessType", ACCESS_TYPE)
			    .getSingleResult();
	}


	/* (non-Javadoc)
	 * @see name.ruhkopf.jaxrs.server.service.LoginService#findLogin(java.lang.Integer)
	 */
	@Override
	public LoginEntity findLogin(Integer id)
	{
		return persistenceService.getEm().find(LoginEntity.class, id);
	}


	/**
	 * @return the dao
	 */
	public PersistenceService getPersistenceService()
	{
		return persistenceService;
	}


	/**
	 * @param dao the dao to set
	 */
	public void setPersistenceService(PersistenceService dao)
	{
		this.persistenceService = dao;
	}


	/**
	 * @return the encryptionService
	 */
	public EncryptionService getEncryptionService()
	{
		return encryptionService;
	}


	/**
	 * @param encryptionService the encryptionService to set
	 */
	public void setEncryptionService(EncryptionService encryptionService)
	{
		this.encryptionService = encryptionService;
	}

}
