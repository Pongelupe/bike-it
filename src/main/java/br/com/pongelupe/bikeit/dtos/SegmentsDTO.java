package br.com.pongelupe.bikeit.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SegmentsDTO {
	
	List<SegmentExploreDTO> segments;

}
