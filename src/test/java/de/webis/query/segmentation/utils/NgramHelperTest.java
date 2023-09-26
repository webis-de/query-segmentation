package de.webis.query.segmentation.utils;

import org.junit.Assert;
import org.junit.Test;

public class NgramHelperTest {

	@Test
	public void testGetNgramCount() throws Exception {
		long expected = 614838;
		long ngramCount = new NgramHelper().getNgramCount("obama");

		Assert.assertEquals(expected, ngramCount);
	}

	@Test
	public void testGetNgramCountNotListed() throws Exception {
		long expected = -1;
		long ngramCount = new NgramHelper().getNgramCount("obama family treeeeeeee");
		Assert.assertEquals(expected, ngramCount);
	}

	@Test
	public void testGetNgramCountOfSubTwoGram() throws Exception {
		long expected = 336257372;

		String segment = "new york city human resources administration";
		long actual = new NgramHelper().getNgramCountOfSubTwoGram(segment);

		Assert.assertEquals(expected, actual);
	}

	@Test
    public void testQueryNewYorkTimes() throws Exception {
        long expected = 26069786;

        long actual = new NgramHelper().getNgramCountFromNetspeak("new york times");
        
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void testQueryTimesSquare() throws Exception {
        long expected = 1535958;

        long actual = new NgramHelper().getNgramCountFromNetspeak("times square");
        
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testQueryDanceTimesSquare() throws Exception {
        long expected = 509;

        long actual = new NgramHelper().getNgramCountFromNetspeak("dance times square");
        
        Assert.assertEquals(expected, actual);
    }
}
