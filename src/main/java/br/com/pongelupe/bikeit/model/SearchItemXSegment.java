package br.com.pongelupe.bikeit.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.pongelupe.bikeit.dao.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent entity for SearchItemXSegment
 * 
 * @author pongelupe
 *
 */

@Entity
@Table(name = "search_itemxsegment")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class SearchItemXSegment implements BaseEntity<SearchItemXSegmentId>{

	@EmbeddedId
	private SearchItemXSegmentId id;

}
