package br.com.pongelupe.bikeit.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * A base DAO for configuring JPA and defining base operations for Entities
 * 
 * @author pongelupe
 *
 * @param <T>
 */
public class BaseDAO<T extends BaseEntity> {

	protected EntityManager em;

	public BaseDAO() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bikeit");
		em = entityManagerFactory.createEntityManager();
	}

	/**
	 * A simple operation to persist a Entity into the database
	 * 
	 * @param target - a Entity
	 * @return
	 */
	public T persist(T target) {
		em.getTransaction().begin();
		em.persist(target);
		em.getTransaction().commit();
		return target;
	}

	/**
	 * A simple operation to persist a List of Entities into the database <br>
	 * It uses a SINGLE transaction
	 * 
	 * @param target
	 * @return
	 */
	public List<T> persistAll(List<T> target) {
		em.getTransaction().begin();
		target.forEach(em::persist);
		em.getTransaction().commit();
		return target;
	}

}
