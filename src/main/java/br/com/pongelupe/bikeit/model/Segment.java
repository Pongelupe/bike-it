package br.com.pongelupe.bikeit.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.pongelupe.bikeit.dao.BaseEntity;
import lombok.Builder;
import lombok.Data;

/**
 * The persistent entity for Segment
 * 
 * @author pongelupe
 *
 */
@Entity
@Data
@Builder
public class Segment implements BaseEntity {

	@Id
	private int id;
	@Column(name = "name", length = 50)
	private String name;
	private double distance;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonAlias("created_at")
	private Date createdAt;

	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonAlias("updated_at")
	private Date updatedAt;
	
	@JsonAlias("effort_count")
	private int effortCount;
	
	@JsonAlias("athlete_count")
	private int athleteCount;

	@JsonAlias("star_count")
	private int starCount;

	@Lob
	@Type(type = "text")
	private String polyline;
	
}
