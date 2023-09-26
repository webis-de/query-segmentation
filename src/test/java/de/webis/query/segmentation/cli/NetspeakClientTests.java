package de.webis.query.segmentation.cli;

import org.junit.Assert;

import org.junit.Test;

import de.webis.query.segmentation.utils.NgramHelper;


public class NetspeakClientTests {
    @Test
    public void testQueryNewYorkTimes() throws Exception {
        NgramHelper client = new NgramHelper();
        long expected = 26069786;

        long actual = client.getNgramCountFromNetspeak("new york times");
        
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testQueryTimesSquare() throws Exception {
        NgramHelper client = new NgramHelper();
        long expected = 1535958;

        long actual = client.getNgramCountFromNetspeak("times square");
        
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testQueryDanceTimesSquare() throws Exception {
        NgramHelper client = new NgramHelper();
        long expected = 509;

        long actual = client.getNgramCountFromNetspeak("dance times square");
        
        Assert.assertEquals(expected, actual);
    }
}
