package br.com.pongelupe.bikeit.dao.impl;

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
}
