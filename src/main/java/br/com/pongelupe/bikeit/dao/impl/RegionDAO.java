package br.com.pongelupe.bikeit.dao.impl;

import br.com.pongelupe.bikeit.dao.BaseDAO;
import br.com.pongelupe.bikeit.model.Region;

/**
 * It defines methods for database's operations for the {@link Region} Entity
 * 
 * @author pongelupe
 *
 */
public class RegionDAO extends BaseDAO<Region> {

	public RegionDAO() {
		super(Region.class);
	}
}
