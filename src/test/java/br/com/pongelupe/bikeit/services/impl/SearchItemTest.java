package br.com.pongelupe.bikeit.services.impl;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.pongelupe.bikeit.model.Coordinates;
import br.com.pongelupe.bikeit.model.SearchItem;

public class SearchItemTest {

	SearchItem item;

	@Before
	public void setUp() throws Exception {
		item = new SearchItem(-1, null, new Coordinates(-1, -19.972729, -44.024416),
				new Coordinates(-1, -19.802463, -43.909215), 5, false);
	}

	@Test
	public void test_getBounds() {
		assertArrayEquals(new String[] { "-19.972729", "-44.024416", "-19.802463", "-43.909215" }, item.getBounds().toArray());
	}
}
