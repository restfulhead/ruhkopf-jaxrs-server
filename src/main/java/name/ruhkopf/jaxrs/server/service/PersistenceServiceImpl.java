package name.ruhkopf.jaxrs.server.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;


@Component("persistenceService")
public class PersistenceServiceImpl implements PersistenceService
{
	@PersistenceContext
	private EntityManager em;

	@Override
	public void runInTransaction(Closure code)
	{
		code.invoke(em);
	}

	/**
	 * @return the em
	 */
	@Override
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
