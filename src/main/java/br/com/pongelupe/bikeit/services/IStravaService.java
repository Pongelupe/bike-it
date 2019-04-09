package br.com.pongelupe.bikeit.services;

import java.util.List;

import br.com.pongelupe.bikeit.dtos.SegmentExploreDTO;
import br.com.pongelupe.bikeit.exceptions.RequestException;
import br.com.pongelupe.bikeit.model.Auth;
import br.com.pongelupe.bikeit.model.Segment;

/**
 * Service Interface to Strava's v3 API
 * 
 * @author pongelupe
 *
 */
public interface IStravaService {

	/**
	 * Retrieves the proper authorization to consume the Strava's v3 API Service.
	 * <br>
	 * Also the authorization credentials are set on future requests header by
	 * calling {@link IStravaService#setTokenOnRequestHeader(Auth)}
	 * 
	 * @return
	 * @throws RequestException
	 * @see IStravaService#setTokenOnRequestHeader(Auth)
	 */
	Auth getOauthToken() throws RequestException;

	/**
	 * Sets authorization credentials on HTTP request header
	 * 
	 * @param auth
	 * 
	 * @see BaseService#setTokenOnRequestHeader(Auth)
	 */
	void setTokenOnRequestHeader(Auth auth);

	/**
	 * Retrieves from Strava's API the segments defined within the passed bounds
	 * 
	 * @param bounds - e.g.:
	 *               Arrays.asList("-19.972729","-44.024416","-19.802463","-43.909215");
	 *               <br>
	 *               - it MUST have four Strings as parameter and the order MATTERS!
	 *               <br>
	 *               Order explanation:
	 *               <ol>
	 *               <li>Southwest corner latitude</li>
	 *               <li>Southwest corner longitude</li>
	 *               <li>Northeast corner latitude</li>
	 *               <li>Northeast corner longitude</li>
	 *               </ol>
	 * @return List<{@link Segment}> - the segments defined within the passed bounds
	 * @throws RequestException
	 */
	List<SegmentExploreDTO> exploreSegments(List<String> bounds) throws RequestException;

	Segment getSegment(int idSegment) throws RequestException;

}
