package br.com.pongelupe.bikeit.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent entity for SearchItemXSegment
 * 
 * @author pongelupe
 *
 */

@Entity
@Table(name = "search_itemxsegment")
public class SearchItemXSegment {

	@EmbeddedId
	private SearchItemXSegmentId id;

}
