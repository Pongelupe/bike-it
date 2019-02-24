package br.com.pongelupe.bikeit.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BaseService {

	private static final String BIKE_IT_PROPERTIES = "/bike-it.properties";
	protected static final String BASE_URL = "https://www.strava.com/api/v3/";
	protected Properties prop;
	protected OkHttpClient client;
	protected ObjectMapper mapper = new ObjectMapper();

	public BaseService() {
		client = new OkHttpClient();
		try {
			prop = new Properties();
			prop.load(new FileInputStream(BaseService.class.getResource(BIKE_IT_PROPERTIES).getFile()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getProp(String propKey) {
		return this.prop.get(propKey).toString();
	}
	
	protected <T> T doRequest(Request request, Class<T> reponseBody) {
		Call call = client.newCall(request);
		Response execute = null;
		try {
			execute = call.execute();
			return mapper.readValue(execute.body().bytes(), reponseBody);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}