package br.com.pongelupe.bikeit.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.pongelupe.bikeit.dao.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent entity for Search
 * 
 * @author pongelupe
 *
 */

@Entity
@Data
@NoArgsConstructor
public class Search implements BaseEntity<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date searchAt;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_region")
	private Region region;
	
	@Column(name = "id_region", insertable = false, updatable = false)
	private int regionId;
	
	private boolean completed;

	public Search(Region region) {
		this.region = region;
		this.searchAt = new Date();
	}

}
