package br.com.pongelupe.bikeit;

import br.com.pongelupe.bikeit.model.Auth;
import br.com.pongelupe.bikeit.services.impl.StravaService;

public class App {

	public static void main(String... args) {
		StravaService stravaService = new StravaService();
		Auth oauthToken = stravaService.getOauthToken();
		System.out.println(oauthToken);
		System.out.println(stravaService.exploreSegments());
		System.exit(0);
	}
}
