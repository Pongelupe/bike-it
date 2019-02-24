package br.com.pongelupe.bikeit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Auth {

	private String token_type;
	private String access_token;

}
