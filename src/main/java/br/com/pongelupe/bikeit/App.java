package br.com.pongelupe.bikeit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import br.com.pongelupe.bikeit.dao.impl.CoordinatesDAO;
import br.com.pongelupe.bikeit.dao.impl.RegionDAO;
import br.com.pongelupe.bikeit.dao.impl.SearchDAO;
import br.com.pongelupe.bikeit.dao.impl.SearchItemDAO;
import br.com.pongelupe.bikeit.dao.impl.SearchItemXSegmentDAO;
import br.com.pongelupe.bikeit.dao.impl.SegmentDAO;
import br.com.pongelupe.bikeit.dao.impl.SegmentHistoricalDAO;
import br.com.pongelupe.bikeit.dtos.SegmentExploreDTO;
import br.com.pongelupe.bikeit.exceptions.BikeItException;
import br.com.pongelupe.bikeit.exceptions.RequestException;
import br.com.pongelupe.bikeit.model.Auth;
import br.com.pongelupe.bikeit.model.Region;
import br.com.pongelupe.bikeit.model.Search;
import br.com.pongelupe.bikeit.model.SearchItem;
import br.com.pongelupe.bikeit.model.SearchItemXSegment;
import br.com.pongelupe.bikeit.model.SearchItemXSegmentId;
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
		int idRegion = Integer.parseInt(args[0]);
		Optional<Region> regionOptional = new RegionDAO().findById(idRegion);

		SearchDAO searchDAO = new SearchDAO();
		Region region = regionOptional.orElseThrow();
		boolean existsOngoing = searchDAO.existsOngoing(region.getId());
		SearchItemDAO searchItemDAO = new SearchItemDAO();

		List<SearchItem> itemsSearch = new ArrayList<>();
		Search search = existsOngoing ? searchDAO.findOngoingByIdRegion(region.getId())
				: searchDAO.persist(new Search(region));
		if (existsOngoing) {
			itemsSearch = searchItemDAO.findItemsToBeProcessed(search.getId());
		} else {
			itemsSearch = new CoordinatesDAO().prepareCoordinatesForSearchItemByRegion(idRegion);
			itemsSearch.forEach(item -> item.setSearch(search));
			searchItemDAO.persistAll(itemsSearch);
			itemsSearch = itemsSearch.size() > 55 ? itemsSearch.subList(0, 54) : itemsSearch;
		}

		SegmentHistoricalDAO segmentHistoricalDAO = new SegmentHistoricalDAO();
		SegmentDAO segmentDAO = new SegmentDAO();

		SearchItemXSegmentDAO searchItemXSegmentDAO = new SearchItemXSegmentDAO();
		IStravaService stravaService = new StravaService();
		itemsSearch.forEach(item -> {
			boolean processed = true;
			try {
				retriveAuth(stravaService);
				List<SegmentExploreDTO> exploreSegments = stravaService.exploreSegments(item.getBounds());
				LOGGER.info(() -> exploreSegments.size() + " segments found @ " + item.getBounds());

				exploreSegments.stream().forEach(segmentExplored -> {
					Segment segment;
					try {
						segment = stravaService.getSegment(segmentExplored.getId());
						segmentDAO.persistIfNotExistsElse(segment,
								s -> segmentHistoricalDAO.persistIfNotExists(s.toHistorical()));
						searchItemXSegmentDAO.persist(
								new SearchItemXSegment(new SearchItemXSegmentId(item.getId(), search.getId())));
					} catch (RequestException e) {
						LOGGER.severe("Eror requesting " + e.getMessage());
					}
				});

			} catch (RequestException e) {
				LOGGER.severe("Eror requesting " + e.getMessage());
				processed = false;
			} finally {
				item.setSearch(search);
				item.setProcessed(processed);
				searchItemDAO.persist(item);
			}
		});
		if (itemsSearch.isEmpty()) {
			search.setCompleted(true);
			searchDAO.update(search);
		}
		System.exit(0);

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
