package br.com.pongelupe.bikeit.services;

import java.util.List;

import br.com.pongelupe.bikeit.exceptions.RequestException;
import br.com.pongelupe.bikeit.model.Auth;
import br.com.pongelupe.bikeit.model.Segment;

public interface IStravaService {

	Auth getOauthToken() throws RequestException;

	void setTokenOnRequestHeader(Auth auth);

	List<Segment> exploreSegments() throws RequestException;

}
