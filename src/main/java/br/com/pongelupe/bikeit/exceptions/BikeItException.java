package br.com.pongelupe.bikeit.exceptions;


/**
 * This exception mean to deal with the propertie file
 * {@linkplain /src/main/resources/bike-it.properties}
 * 
 * @author pongelupe
 *
 */
public class BikeItException extends Exception {

	public BikeItException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = -8848999986566916289L;

}
