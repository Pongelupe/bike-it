package br.com.pongelupe.bikeit.services.impl;

import br.com.pongelupe.bikeit.model.Auth;
import br.com.pongelupe.bikeit.services.BaseService;
import br.com.pongelupe.bikeit.services.IStravaService;
import okhttp3.FormBody;
import okhttp3.Request;

public class StravaService extends BaseService implements IStravaService {

	@Override
	public Auth getOauthToken() {
		Request request = new Request.Builder().url(BASE_URL + "oauth/token")
				.post(new FormBody.Builder().add("client_id", getProp("client_id"))
						.add("client_secret", getProp("client_secret")).add("code", getProp("code")).build())
				.build();
		return doRequest(request, Auth.class);
	}
}
