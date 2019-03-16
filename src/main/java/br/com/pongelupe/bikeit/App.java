package br.com.pongelupe.bikeit;

import java.util.logging.Logger;

import br.com.pongelupe.bikeit.exceptions.BikeItException;
import br.com.pongelupe.bikeit.exceptions.RequestException;
import br.com.pongelupe.bikeit.model.Auth;
import br.com.pongelupe.bikeit.services.IPropertyService;
import br.com.pongelupe.bikeit.services.IStravaService;
import br.com.pongelupe.bikeit.services.impl.PropertyService;
import br.com.pongelupe.bikeit.services.impl.StravaService;

/**
 * 
 * @author pongelupe
 *
 */
public class App {

	private static final Logger LOGGER = Logger.getLogger(App.class.getName());

	public static void main(String... args) {
		try {
			IStravaService stravaService = new StravaService();
			retriveAuth(stravaService);
			System.out.println(stravaService.exploreSegments());
		} catch (RequestException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	/**
	 * This method retrieves the Auth for the HTTP request and sets it on the HTTP
	 * header for future requests. It first tries to recover from
	 * bike-it.properties. Case the property was not set yet, it searches from the
	 * Strava v3 API
	 * 
	 * @param stravaService
	 * @throws RequestException - when tries to request to retrieve the HTTP token
	 */
	private static void retriveAuth(IStravaService stravaService) throws RequestException {
		try {
			IPropertyService propertyService = new PropertyService();
			String tokenType = propertyService.getProp("auth.token_type")
					.orElseThrow(() -> new BikeItException("auth.token_type not found!"));
			String token = propertyService.getProp("auth.token")
					.orElseThrow(() -> new BikeItException("auth.token not found!"));
			LOGGER.fine("Auth object retrive from properties");
			stravaService.setTokenOnRequestHeader(new Auth(tokenType, token));
		} catch (BikeItException e) {
			LOGGER.severe(e.getMessage());
			stravaService.getOauthToken();
		}
	}
}
