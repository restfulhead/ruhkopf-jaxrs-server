package name.ruhkopf.jaxrs.server.service;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;



public interface PersistenceService
{
	@Transactional
	void runInTransaction(Closure code);

	EntityManager getEm();

	/**
	 * Closure interface.
	 * 
	 * @author Patrick Ruhkopf
	 */
	public interface Closure
	{
		/**
		 * Invoke the closure.
		 */
		public void invoke(EntityManager em);
	}
}
