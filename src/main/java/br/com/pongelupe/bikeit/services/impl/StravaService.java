package br.com.pongelupe.bikeit.services.impl;

import java.util.List;

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
	public Auth getOauthToken() {
		Request request = new Request.Builder().url(BASE_URL + "oauth/token")
				.post(new FormBody.Builder().add("client_id", getProp("client_id"))
						.add("client_secret", getProp("client_secret")).add("code", getProp("code")).build())
				.build();
		Auth auth = doRequest(request, Auth.class);
		setTokenOnRequestHeader(auth);
		return auth;
	}
	
	@Override
	public List<Segment> exploreSegments() {
		Request request = new Request.Builder()
				.url(new HttpUrl.Builder()
						   .scheme("https")
					       .host("www.strava.com")
					       .addPathSegment("api")
					       .addPathSegment("v3")
					       .addPathSegment("segments")
					       .addPathSegment("explore")
					       .addQueryParameter("bounds", "-19.972729,-44.024416,-19.802463,-43.909215")
					       .addQueryParameter("activity_type","riding")
					       .addQueryParameter("min_cat", "0")
					       .addQueryParameter("max_cat", "5600")
					       .build())
				.get()
				.build();
		return doRequest(request, Segments.class).getSegments();
	}
}
