package br.com.pongelupe.bikeit.dao.impl;

import java.util.List;

import javax.persistence.Query;

import br.com.pongelupe.bikeit.dao.BaseDAO;
import br.com.pongelupe.bikeit.model.Coordinates;

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

	//TODO - fazer reativo
	public void getAllCoordsByRegion(int idRegion) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT DISTINCT ON (a.id) a.id, a.xcoord xa, a.ycoord ya, b.xcoord xb, b.ycoord yb, ");
		sql.append("ST_DISTANCESPHERE(a.geom, b.geom) AS d ");
		sql.append("FROM  coordinates a JOIN coordinates b ON ");
		sql.append("a.ycoord < b.ycoord AND a.xcoord < b.xcoord ");
		sql.append("WHERE a.id_region = :idRegion and b.id_region = :idRegion ");
		sql.append("ORDER BY a.id, a.xcoord, a.ycoord, b.xcoord, b.ycoord, ST_DISTANCESPHERE(a.geom, b.geom) ");

		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("idRegion", idRegion);

		query.getResultList().forEach(System.out::println);

	}

}
