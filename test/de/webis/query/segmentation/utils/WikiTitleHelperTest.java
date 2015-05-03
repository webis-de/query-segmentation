package de.webis.query.segmentation.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class WikiTitleHelperTest {

	@Test
	public void testIsWikiTitle() throws Exception {
		assertEquals(true, WikiTitleHelper.isWikiTitle("bauhaus-universitaet weimar"));
		assertEquals(false, WikiTitleHelper.isWikiTitle("barack obamamamamamam"));
	}

}
