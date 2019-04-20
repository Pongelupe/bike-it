package br.com.pongelupe.bikeit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.pongelupe.bikeit.dao.BaseEntity;
import lombok.Data;

/**
 * The persistent entity for Coordinates
 * 
 * @author pongelupe
 *
 */

@Entity
@Data
public class Coordinates implements BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Double xcoord;
	private Double ycoord;

}
