package de.webis.query.segmentation.cli;

import org.junit.Assert;

import org.junit.Test;

import de.webis.query.segmentation.application.QuerySegmentation;
import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.utils.NgramHelper;

public class QuerySegmentationTests {
    private static NgramHelper ngramHelper = new NgramHelper();

    @Test
    public void testWtSnpSegmentationOfNewYorkTimesSquareDance() {
        Query query = new Query("new york times square dance");
        String expected = "new york times square|dance";

        String actual = segmentQuery(query, "wt-snp-baseline");

        Assert.assertEquals(expected, actual.toString());
    }

    @Test
    public void testHypBSegmentationOfNewYorkTimesSquareDance() {
        Query query = new Query("new york times square dance");
        String expected = "new|york|times|square|dance";

        String actual = segmentQuery(query, "hyb-b");

        Assert.assertEquals(expected, actual.toString());
    }

    @Test
    public void testHypASegmentationOfNewYorkTimesSquareDance() {
        Query query = new Query("new york times square dance");
        String expected = "new york times|square dance";

        String actual = segmentQuery(query, "hyb-a");

        Assert.assertEquals(expected, actual.toString());
    }

    @Test
    public void testWikiBasedSegmentationOfNewYorkTimesSquareDance() {
        Query query = new Query("new york times square dance");
        String expected = "new york times|square dance";

        String actual = segmentQuery(query, "wiki-based");

        Assert.assertEquals(expected, actual.toString());
    }

    @Test
    public void testWtSegmentationOfNewYorkTimesSquareDance() {
        Query query = new Query("new york times square dance");
        String expected = "new york times|square dance";

        String actual = segmentQuery(query, "wt-baseline");

        Assert.assertEquals(expected, actual.toString());
    }

    @Test
    public void testNaiveSegmentationOfNewYorkTimesSquareDance() {
        Query query = new Query("new york times square dance");
        String expected = "new york|times square|dance";

        String actual = segmentQuery(query, "naive");

        Assert.assertEquals(expected, actual.toString());
    }

    @Test
    public void testHybISegmentationOfNewYorkTimesSquareDance() {
        Query query = new Query("new york times square dance");
        String expected = "new|york|times|square|dance";

        String actual = segmentQuery(query, "hyb-i");

        Assert.assertEquals(expected, actual.toString());
    }

    // dance times square
    private static String segmentQuery(Query query, String querySegmentation) {
        QuerySegmentation segmenter = App.querySegmentations(ngramHelper).get(querySegmentation);

        return segmenter.performSegmentation(query).toString().split("\t")[1];
    }
}
