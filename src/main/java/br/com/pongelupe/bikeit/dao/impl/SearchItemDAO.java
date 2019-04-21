package br.com.pongelupe.bikeit.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.pongelupe.bikeit.dao.BaseDAO;
import br.com.pongelupe.bikeit.model.SearchItem;

/**
 * It defines methods for database's operations for the {@link SearchItem}
 * Entity
 * 
 * @author pongelupe
 *
 */
public class SearchItemDAO extends BaseDAO<SearchItem> {

	public SearchItemDAO() {
		super(SearchItem.class);
	}

	public List<SearchItem> findItemsToBeProcessed(int idSearch) {
		CriteriaQuery<SearchItem> cq = getCb().createQuery(SearchItem.class);
		Root<SearchItem> from = cq.from(SearchItem.class);

		cq.select(from).where(getCb().and(getCb().equal(from.get("search"), idSearch),
				getCb().equal(from.get("processed"), false)));
		
		return em.createQuery(cq).setMaxResults(55).getResultList();
	}

}
