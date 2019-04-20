package br.com.pongelupe.bikeit.dao.impl;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.pongelupe.bikeit.dao.BaseDAO;
import br.com.pongelupe.bikeit.dao.BaseEntity;
import br.com.pongelupe.bikeit.model.SegmentHistorical;

/**
 * It defines methods for database's operations for the
 * {@link SegmentHistorical} Entity
 * 
 * @author pongelupe
 *
 */
public class SegmentHistoricalDAO extends BaseDAO<SegmentHistorical> {

	public SegmentHistoricalDAO() {
		super(SegmentHistorical.class);
	}
	
	@Override
	public boolean exists(SegmentHistorical entity) {
		CriteriaQuery<Integer> cq = getCb().createQuery(Integer.class);
		Root<? extends BaseEntity> from = cq.from(entity.getClass());

		cq.select(getCb().literal(1)).where(getCb().equal(from.get("updatedAt"), entity.getUpdatedAt()));

		return !em.createQuery(cq).getResultList().isEmpty();
	}

}
