package br.com.pongelupe.bikeit.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Optional;

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
		Optional<String> clientId = propertyService.getProp("client_id");
		Optional<String> clientSecret = propertyService.getProp("client_secret");
		Optional<String> code = propertyService.getProp("code");
		Optional<String> potato = propertyService.getProp("potato");

		assertEquals("99688", clientId.get());
		assertEquals("superSecretTopSecret101", clientSecret.get());
		assertEquals("mySecretCode7849RandomNumbers", code.get());
		assertNotEquals("aPotato", potato.orElse(null));
	}

	@Test
	public void testSetProp() {
		propertyService.setProp("writer", "camus");
		assertEquals("camus", propertyService.getProp("writer").get());
	}

	@Test
	public void testSetPropEager() throws Exception {
		propertyService.setProp("writer", "camus");
		assertEquals("camus", propertyService.getProp("writer").orElse(null));
		FileOutputStream fileOutputStream = new FileOutputStream(this.getClass().getClassLoader().getResource(BIKE_IT_PROPERTIES_TEST).getFile());
		propertyService.getProp()
				.store(fileOutputStream, "BIKE-IT");
		fileOutputStream.close();
		setUp();
		assertEquals("camus", propertyService.getProp("writer").get());
	}

}
