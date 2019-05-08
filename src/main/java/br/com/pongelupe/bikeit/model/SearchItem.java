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
		var upperInitialY = initialCoord.getYcoord() > finalCoord.getYcoord();
		var upperInitialX = initialCoord.getXcoord() > finalCoord.getXcoord();

		var initialCoordPoint = new String[2];
		var finalCoordPoint = new String[2];

		if (upperInitialY && upperInitialX) {
			initialCoordPoint[0] = String.valueOf(finalCoord.getYcoord());
			initialCoordPoint[1] = String.valueOf(finalCoord.getXcoord());

			finalCoordPoint[0] = String.valueOf(initialCoord.getYcoord());
			finalCoordPoint[1] = String.valueOf(initialCoord.getXcoord());
		} else if (!upperInitialY && !upperInitialX) {
			initialCoordPoint[0] = String.valueOf(initialCoord.getYcoord());
			initialCoordPoint[1] = String.valueOf(initialCoord.getXcoord());

			finalCoordPoint[0] = String.valueOf(finalCoord.getYcoord());
			finalCoordPoint[1] = String.valueOf(finalCoord.getXcoord());
		}

		return Arrays.asList(initialCoordPoint[0], initialCoordPoint[1], finalCoordPoint[0], finalCoordPoint[1]);
	}

}
