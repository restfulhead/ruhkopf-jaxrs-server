package name.ruhkopf.jaxrs.server.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import name.ruhkopf.jaxrs.server.model.User;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component("userService")
public class UserServiceImpl
{
	@PersistenceContext
	private EntityManager em;


	@Transactional
	public void createNewUser(User user)
	{
		em.persist(user);
	}

	public List<User> findAllContacts(int start, int count)
	{
		CriteriaQuery<User> criteria = em.getCriteriaBuilder().createQuery(User.class);
		criteria.from(User.class);
		TypedQuery<User> query = em.createQuery(criteria);
		query.setFirstResult(start);
		query.setMaxResults(count);
		return query.getResultList();
	}

	public User findUser(Integer id)
	{
		return em.find(User.class, id);
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
