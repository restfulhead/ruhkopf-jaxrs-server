package name.ruhkopf.jaxrs.server.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import name.ruhkopf.jaxrs.server.model.LoginEntity;
import name.ruhkopf.jaxrs.server.util.Precondition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component("usernamePasswordLoginService")
public class UsernamePasswordLoginService
{
	private static Logger LOG = LogManager.getLogger();
	
	protected final static String ACCESS_TYPE = "password";
	
	@PersistenceContext
	private EntityManager em;

	@Inject
	private EncryptionService encryptionService;

	@Transactional
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

	public List<LoginEntity> findAllLogins(int start, int count)
	{
		CriteriaQuery<LoginEntity> criteria = em.getCriteriaBuilder().createQuery(LoginEntity.class);
		criteria.from(LoginEntity.class);
		TypedQuery<LoginEntity> query = em.createQuery(criteria);
		query.setFirstResult(start);
		query.setMaxResults(count);
		return query.getResultList();
	}

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
