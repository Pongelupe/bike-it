package br.com.pongelupe.bikeit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.pongelupe.bikeit.dao.BaseEntity;
import lombok.Data;

/**
 * The persistent entity for Segment
 * @author pongelupe
 *
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Segment implements BaseEntity {

	@Id
	private int id;
	@Column(name = "name", length = 50)
	private String name;
	private double distance;
	@Lob
	@Type(type = "text")
	private String points;
}
