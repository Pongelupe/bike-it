package br.com.pongelupe.bikeit.services.impl;

import static org.junit.Assert.assertEquals;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class PropertyServiceTest {

	private PropertyService propertyService;
	private static final String BIKE_IT_PROPERTIES_TEST = "bike-it.properties";

	@Before
	public void setUp() throws Exception {
		propertyService = new PropertyService();
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(BIKE_IT_PROPERTIES_TEST);
		propertyService.getProp().load(resourceAsStream);
		resourceAsStream.close();
	}

	@Test
	public void testGetProp() {
		String clientId = propertyService.getProp("client_id");
		String clientSecret = propertyService.getProp("client_secret");
		String code = propertyService.getProp("code");

		assertEquals("99688", clientId);
		assertEquals("superSecretTopSecret101", clientSecret);
		assertEquals("mySecretCode7849RandomNumbers", code);
	}

	@Test
	public void testSetProp() {
		propertyService.setProp("writer", "camus");
		assertEquals("camus", propertyService.getProp("writer"));
	}

	@Test
	public void testSetPropEager() throws Exception {
		propertyService.setProp("writer", "camus");
		assertEquals("camus", propertyService.getProp("writer"));
		FileOutputStream fileOutputStream = new FileOutputStream(this.getClass().getClassLoader().getResource(BIKE_IT_PROPERTIES_TEST).getFile());
		propertyService.getProp()
				.store(fileOutputStream, "BIKE-IT");
		fileOutputStream.close();
		setUp();
		assertEquals("camus", propertyService.getProp("writer"));
	}

}
