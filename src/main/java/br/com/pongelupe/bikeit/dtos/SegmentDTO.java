package br.com.pongelupe.bikeit.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.pongelupe.bikeit.model.Segment;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SegmentDTO {

	private int id;
	private String name;
	private double distance;
	
	@JsonAlias("created_at")
	private Date createdAt;
	
	@JsonAlias("updated_at")
	private Date updatedAt;

	@JsonAlias("effort_count")
	private int effortCount;

	@JsonAlias("athlete_count")
	private int athleteCount;

	@JsonAlias("star_count")
	private int starCount;
	
	private MapDTO map;

	public Segment toSegment() {
		return Segment.builder()
				.id(id)
				.name(name)
				.distance(distance)
				.createdAt(createdAt)
				.updatedAt(updatedAt)
				.effortCount(effortCount)
				.athleteCount(athleteCount)
				.starCount(starCount)
				.polyline(map.getPolyline())
				.build();
	}

}
