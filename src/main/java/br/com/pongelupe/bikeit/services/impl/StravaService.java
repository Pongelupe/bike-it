package br.com.pongelupe.bikeit.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import br.com.pongelupe.bikeit.exceptions.RequestException;
import br.com.pongelupe.bikeit.model.Auth;
import br.com.pongelupe.bikeit.model.Segment;
import br.com.pongelupe.bikeit.model.Segments;
import br.com.pongelupe.bikeit.services.BaseService;
import br.com.pongelupe.bikeit.services.IStravaService;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class StravaService extends BaseService implements IStravaService {

	@Override
	public Auth getOauthToken() throws RequestException {
		Request request = new Request.Builder().url(BASE_URL + "oauth/token")
				.post(new FormBody.Builder().add("client_id", propertyService.getProp("client_id").orElseThrow())
						.add("client_secret", propertyService.getProp("client_secret").orElseThrow())
						.add("code", propertyService.getProp("code").orElseThrow()).build())
				.build();
		Auth auth = doRequest(request, Auth.class);
		setTokenOnRequestHeader(auth);

		try {
			propertyService.setProp("auth.token_type", auth.getToken_type());
			propertyService.setProp("auth.token", auth.getAccess_token());
			propertyService.commitChanges();
		} catch (IOException e) {
			propertyService.setPropEager("auth.token_type", auth.getToken_type());
			propertyService.setPropEager("auth.token", auth.getAccess_token());
		}
		return auth;

	}

	@Override
	public void setTokenOnRequestHeader(Auth auth) {
		super.setTokenOnRequestHeader(auth);
	}

	@Override
	public List<Segment> exploreSegments(List<String> bounds) throws RequestException {
		Request request = new Request.Builder().url(new HttpUrl.Builder().scheme("https").host("www.strava.com")
				.addPathSegment("api").addPathSegment("v3").addPathSegment("segments").addPathSegment("explore")
				.addQueryParameter("bounds", boundsToQueryParameter(bounds))
				.addQueryParameter("activity_type", "riding").addQueryParameter("min_cat", "0")
				.addQueryParameter("max_cat", "5600").build()).get().build();
		return doRequest(request, Segments.class).getSegments();
	}

	private String boundsToQueryParameter(List<String> bounds) {
		if (bounds == null || bounds.size() != 4) {
			throw new IllegalArgumentException("Bounds size must be equals to 4");
		} else {
			return bounds.stream().collect(Collectors.joining(","));
		}
	}

}
