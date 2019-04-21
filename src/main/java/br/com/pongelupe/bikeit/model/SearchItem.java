package br.com.pongelupe.bikeit.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.pongelupe.bikeit.dao.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent entity for SearchItem
 * 
 * @author pongelupe
 *
 */

@Entity
@Table(name = "search_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchItem implements BaseEntity<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_search")
	private Search search;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_initial_coord")
	private Coordinates initialCoord;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_final_coord")
	private Coordinates finalCoord;

	private double distance;
	
	private boolean processed;

	public List<String> getBounds() {
		return Arrays.asList(String.valueOf(initialCoord.getXcoord()), String.valueOf(initialCoord.getYcoord()),
				String.valueOf(finalCoord.getXcoord()), String.valueOf(finalCoord.getYcoord()));
	}

}
