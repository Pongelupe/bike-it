package br.com.pongelupe.bikeit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Segment {

	private int id;
	private String name;
	private double distance;
	private String points;
}
