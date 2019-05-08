package br.com.pongelupe.bikeit.services.impl;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import br.com.pongelupe.bikeit.model.Coordinates;
import br.com.pongelupe.bikeit.model.SearchItem;

public class SearchItemTest {

	@Test
	public void test_getBounds_1() {
		var item = new SearchItem(-1, null, new Coordinates(-1, -43.909215, -19.802463),
				new Coordinates(-1, -44.024416, -19.972729), 5, false);
		assertArrayEquals(new String[] { "-19.972729", "-44.024416", "-19.802463", "-43.909215" },
				item.getBounds().toArray());
	}

	@Test
	public void test_getBounds_2() {
		var item = new SearchItem(-1, null, new Coordinates(-1, -44.024416, -19.972729),
				new Coordinates(-1, -43.909215, -19.802463), 5, false);
		assertArrayEquals(new String[] { "-19.972729", "-44.024416", "-19.802463", "-43.909215" },
				item.getBounds().toArray());
	}
}
