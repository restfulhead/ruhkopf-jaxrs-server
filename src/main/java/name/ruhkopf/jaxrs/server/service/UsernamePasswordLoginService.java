package name.ruhkopf.jaxrs.server.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import name.ruhkopf.jaxrs.server.model.LoginEntity;
import name.ruhkopf.jaxrs.server.util.Precondition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


@Component("usernamePasswordLoginService")
public class UsernamePasswordLoginService implements LoginServiceProvider
{
	private static Logger LOG = LogManager.getLogger();
	
	protected final static String ACCESS_TYPE = "password";
	
	@PersistenceContext
	private EntityManager em;

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
		
		LoginEntity login = new LoginEntity(userToken, encryptedToken, ACCESS_TYPE);
		LOG.info("Creating new login for {} (type='{}')", userToken, ACCESS_TYPE);
		em.persist(login);
		return login;
	}

	/* (non-Javadoc)
	 * @see name.ruhkopf.jaxrs.server.service.LoginService#findAllLogins(int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoginEntity> findAllLogins(int start, int count)
	{
		return em.createNamedQuery("findAllByAccessType")
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
		return (long) em.createNamedQuery("countAllByAccessType")
			    .setParameter("accessType", ACCESS_TYPE)
			    .getSingleResult();
	}


	/* (non-Javadoc)
	 * @see name.ruhkopf.jaxrs.server.service.LoginService#findLogin(java.lang.Integer)
	 */
	@Override
	public LoginEntity findLogin(Integer id)
	{
		return em.find(LoginEntity.class, id);
	}


	/**
	 * @return the em
	 */
	public EntityManager getEm()
	{
		return em;
	}

	/**
	 * @param em the em to set
	 */
	public void setEm(EntityManager em)
	{
		this.em = em;
	}

}
