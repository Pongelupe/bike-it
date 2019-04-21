package br.com.pongelupe.bikeit.dao.impl;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.pongelupe.bikeit.dao.BaseDAO;
import br.com.pongelupe.bikeit.model.Search;

/**
 * It defines methods for database's operations for the {@link Search} Entity
 * 
 * @author pongelupe
 *
 */
public class SearchDAO extends BaseDAO<Search> {

	public SearchDAO() {
		super(Search.class);
	}

	public Search findOngoingByIdRegion(int idRegion) {
		CriteriaQuery<Search> cq = getCb().createQuery(Search.class);
		Root<Search> from = cq.from(Search.class);
		cq.select(from).where(getCb().and(getCb().equal(from.get("completed"), false),
				getCb().equal(from.get("region"), idRegion)));

		return em.createQuery(cq).getSingleResult();
	}

	public boolean existsOngoing(int idRegion) {
		CriteriaQuery<Integer> cq = getCb().createQuery(Integer.class);
		Root<Search> from = cq.from(Search.class);

		cq.select(getCb().literal(1)).where(getCb().and(getCb().equal(from.get("completed"), false),
				getCb().equal(from.get("region"), idRegion)));

		return !em.createQuery(cq).getResultList().isEmpty();
	}
}
