package br.com.pongelupe.bikeit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.pongelupe.bikeit.dao.BaseEntity;
import lombok.Data;

/**
 * The persistent entity for Region
 * 
 * @author pongelupe
 *
 */

@Entity
@Data
public class Region implements BaseEntity<Integer> {

	@Id
	private Integer id;

	@Column(name = "des_region", length = 30, nullable = false)
	private String descriptionRegion;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_middle_coord")
	private Coordinates middleCoord;
	
	@Column(name = "id_middle_coord", insertable = false, updatable = false)
	private int middleCoordId;

}
