package de.webis.query.segmentation.utils;

import junit.framework.Assert;

import org.junit.Test;


public class SnpHelperTest {

	@Test
	public void testIsSnp() throws Exception {
		Assert.assertFalse(SnpHelper.isSnp("i am a teapot"));
		Assert.assertTrue(SnpHelper.isSnp("green house number 45"));
	}

}
