package br.com.pongelupe.bikeit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.pongelupe.bikeit.dao.BaseEntity;
import lombok.Data;

/**
 * The persistent entity for SearchItem
 * 
 * @author pongelupe
 *
 */

@Entity
@Table(name = "search_item")
@Data
public class SearchItem implements BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

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
}
