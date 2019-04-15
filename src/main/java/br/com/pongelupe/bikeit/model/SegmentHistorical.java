package br.com.pongelupe.bikeit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.pongelupe.bikeit.dao.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent entity for SegmentHistorical
 * 
 * @author pongelupe
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SegmentHistorical implements BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updatedAt;

	private int effortCount;

	private int athleteCount;

	private int starCount;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "segment_id")
	private Segment originalSegment;

}
