package br.com.pongelupe.bikeit.services;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pongelupe.bikeit.exceptions.RequestException;
import br.com.pongelupe.bikeit.model.Auth;
import br.com.pongelupe.bikeit.services.impl.PropertyService;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BaseService {
	
	protected static final String BASE_URL = "https://www.strava.com/api/v3/";
	protected OkHttpClient client;
	protected ObjectMapper mapper = new ObjectMapper();
	protected IPropertyService propertyService;

	public BaseService() {
		client = new OkHttpClient();
		propertyService = new PropertyService();
	}

	protected <T> T doRequest(Request request, Class<T> reponseBody) throws RequestException {
		Call call = client.newCall(request);
		Response response = null;
		try {
			response = call.execute();
			return mapper.readValue(response.body().bytes(), reponseBody);
		} catch (IOException e) {
			throw new RequestException(request, response);
		}
	}

	/**
	 * This interceptor sets the auth token onto the request and sets a 15 secs read
	 * timeout. The timeout was intended for high delayed operations such as
	 * 
	 * segments/explore
	 * 
	 * @param auth
	 */
	protected void setTokenOnRequestHeader(Auth auth) {
		client = client.newBuilder().readTimeout(15, TimeUnit.SECONDS).addInterceptor(chain -> {
			Request original = chain.request();

			Request request = original.newBuilder().header("User-Agent", "Bike-It")
					.header("Authorization", auth.getToken_type() + " " + auth.getAccess_token())
					.method(original.method(), original.body()).build();

			return chain.proceed(request);
		}).build();
	}

}
