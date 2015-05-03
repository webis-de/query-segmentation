package de.webis.query.segmentation.utils;

import junit.framework.Assert;

import org.junit.Test;

public class NgramHelperTest {

	@Test
	public void testGetNgramCount() throws Exception {
		long ngramCount = NgramHelper.getNgramCount("obama");
		Assert.assertEquals(ngramCount, 565739L);
	}

	@Test
	public void testGetNgramCountNotListed() throws Exception {
		long ngramCount = NgramHelper.getNgramCount("obama family treeeeeeee");
		Assert.assertEquals(ngramCount, -1);
	}

	@Test
	public void testGetNgramCountOfSubTwoGram() throws Exception {
		String segment = "new york city human resources administration";
		Assert.assertEquals(NgramHelper.getNgramCountOfSubTwoGram(segment),
				165357897L);
	}

}
