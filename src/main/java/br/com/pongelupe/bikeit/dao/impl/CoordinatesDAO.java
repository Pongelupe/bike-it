package br.com.pongelupe.bikeit.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Query;

import br.com.pongelupe.bikeit.dao.BaseDAO;
import br.com.pongelupe.bikeit.model.Coordinates;
import br.com.pongelupe.bikeit.model.SearchItem;

/**
 * It defines methods for database's operations for the {@link Coordinates}
 * Entity
 * 
 * @author pongelupe
 *
 */
public class CoordinatesDAO extends BaseDAO<Coordinates> {

	public CoordinatesDAO() {
		super(Coordinates.class);
	}

	@SuppressWarnings("unchecked")
	public List<SearchItem> prepareCoordinatesForSearchItemByRegion(int idRegion) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT DISTINCT ON (a.id) a.id, a.xcoord xa, a.ycoord ya, b.id idb, b.xcoord xb, b.ycoord yb, ");
		sql.append("ST_DISTANCESPHERE(a.geom, b.geom) AS d ");
		sql.append("FROM  coordinates a JOIN coordinates b ON ");
		sql.append("a.ycoord < b.ycoord AND a.xcoord < b.xcoord ");
		sql.append("WHERE a.id_region = :idRegion and b.id_region = :idRegion ");
		sql.append("ORDER BY a.id, a.xcoord, a.ycoord, b.xcoord, b.ycoord, ST_DISTANCESPHERE(a.geom, b.geom) ");

		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("idRegion", idRegion);

		List<Object[]> resultList = query.getResultList();
		return resultList.stream()
				.map(res -> SearchItem.builder()
						.initialCoord(new Coordinates((int) res[0], (double) res[1], (double) res[2]))
						.finalCoord(new Coordinates((int) res[3], (double) res[4], (double) res[5]))
						.distance((double) res[6])
						.build())
				.collect(Collectors.toList());

	}

}
