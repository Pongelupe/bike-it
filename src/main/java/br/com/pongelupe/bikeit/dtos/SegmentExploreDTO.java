package br.com.pongelupe.bikeit.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SegmentExploreDTO {

	private int id;
	
}
