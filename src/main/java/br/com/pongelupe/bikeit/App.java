package br.com.pongelupe.bikeit;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import br.com.pongelupe.bikeit.dao.impl.SegmentDAO;
import br.com.pongelupe.bikeit.dao.impl.SegmentHistoricalDAO;
import br.com.pongelupe.bikeit.dtos.SegmentExploreDTO;
import br.com.pongelupe.bikeit.exceptions.BikeItException;
import br.com.pongelupe.bikeit.exceptions.RequestException;
import br.com.pongelupe.bikeit.model.Auth;
import br.com.pongelupe.bikeit.model.Segment;
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
			SegmentDAO segmentDAO = new SegmentDAO();
			SegmentHistoricalDAO segmentHistoricalDAO = new SegmentHistoricalDAO();
			IStravaService stravaService = new StravaService();
			retriveAuth(stravaService);
			List<SegmentExploreDTO> exploreSegments = stravaService
					.exploreSegments(Arrays.asList("-19.972729", "-44.024416", "-19.802463", "-43.909215"));
			LOGGER.info(() -> exploreSegments.size() + " segments found!");

			exploreSegments.stream().forEach(segmentExplored -> {
				Segment segment;
				try {
					segment = stravaService.getSegment(segmentExplored.getId());
					segmentDAO.persistIfNotExistsElse(segment, s -> segmentHistoricalDAO.persistIfNotExists(s.toHistorical()));
				} catch (RequestException e) {
					LOGGER.severe("Eror requesting " + e.getMessage());
				}
			});

		} catch (RequestException e) {
			LOGGER.severe("Eror requesting " + e.getMessage());
		} finally {
			System.exit(0);
		}
	}

	/**
	 * This method retrieves the Auth for the HTTP request and sets it on the HTTP
	 * header for future requests. It first tries to recover from
	 * bike-it.properties. Case the property was not set yet, it searches from
	 * Strava's v3 API
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
			LOGGER.info("Auth object retrive from properties");
			stravaService.setTokenOnRequestHeader(new Auth(tokenType, token));
		} catch (BikeItException e) {
			LOGGER.info(e.getMessage());
			stravaService.getOauthToken();
		}
	}
}
