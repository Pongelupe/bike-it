package br.com.pongelupe.bikeit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
public class Region implements BaseEntity {

	@Id
	private int id;

	@Column(name = "des_region", length = 30, nullable = false)
	private String descriptionRegion;

}
