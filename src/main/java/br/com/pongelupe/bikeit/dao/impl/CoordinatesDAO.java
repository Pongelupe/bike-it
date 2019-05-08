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

		sql.append("SELECT M.id MID, M.xcoord MX, M.ycoord MY, A.id AID, A.xcoord AX, A.ycoord AY FROM ");
		sql.append("coordinates M, coordinates A ");
		sql.append("JOIN coordinatesxregion CXR ON CXR.id_region = :idRegion AND CXR.id_coordinates = A.id ");
		sql.append("WHERE m.id = (SELECT id_middle_coord FROM region WHERE id = CXR.id_region) AND ");
		sql.append("((m.xcoord < a.xcoord AND m.ycoord < a.ycoord) OR (m.xcoord > a.xcoord AND m.ycoord > a.ycoord)) ");

		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("idRegion", idRegion);

		List<Object[]> resultList = query.getResultList();
		return resultList.stream()
				.map(res -> SearchItem.builder()
						.initialCoord(new Coordinates((int) res[0], (double) res[1], (double) res[2]))
						.finalCoord(new Coordinates((int) res[3], (double) res[4], (double) res[5]))
						.build())
				.collect(Collectors.toList());

	}

}
