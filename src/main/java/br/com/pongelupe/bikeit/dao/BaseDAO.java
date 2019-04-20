package br.com.pongelupe.bikeit.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * A base DAO for configuring JPA and defining base operations for Entities
 * 
 * @author pongelupe
 *
 * @param <T>
 */
public class BaseDAO<T extends BaseEntity> {

	protected EntityManager em;
	protected Class<T> clazz;

	public BaseDAO(Class<T> clazz) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bikeit");
		em = entityManagerFactory.createEntityManager();
		this.clazz = clazz;
	}

	protected CriteriaBuilder getCb() {
		return em.getCriteriaBuilder();
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

	public Optional<T> findById(int id) {
		em.getTransaction().begin();
		T entity = em.find(clazz, id);
		em.getTransaction().commit();

		return Optional.ofNullable(entity);
	}

	public T persistIfNotExists(T target) {
		return persistIfNotExistsElse(target, t -> {
		});
	}

	public T persistIfNotExistsElse(T target, Consumer<T> then) {
		em.getTransaction().begin();
		if (notExists(target)) {
			em.persist(target);
		} else {
			then.accept(target);
		}
		em.getTransaction().commit();
		return target;
	}

	public List<T> persistAllIfNotExists(List<T> target) {
		em.getTransaction().begin();
		target.stream().filter(this::notExists).forEach(em::persist);
		em.getTransaction().commit();
		return target;
	}

	public boolean exists(T entity) {
		CriteriaQuery<Integer> cq = getCb().createQuery(Integer.class);
		Root<? extends BaseEntity> from = cq.from(entity.getClass());

		cq.select(getCb().literal(1)).where(getCb().equal(from.get("id"), entity.getId()));

		return !em.createQuery(cq).getResultList().isEmpty();
	}

	public boolean notExists(T entity) {
		return !exists(entity);
	}

}
