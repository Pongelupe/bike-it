package br.com.pongelupe.bikeit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.pongelupe.bikeit.dao.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent entity for Coordinates
 * 
 * @author pongelupe
 *
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates implements BaseEntity<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Double xcoord;
	private Double ycoord;

}
