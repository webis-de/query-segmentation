package de.webis.query.segmentation.cli;


import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class IoTests {
    @Test
    public void testParsingOfQueries() {
        Map<String, String> actual = App.parseQueries("src/test/resources/cw09-input-without-cache");
        
        Assert.assertEquals("horse hooves", actual.get("51"));
        Assert.assertEquals("avp", actual.get("52"));
        Assert.assertEquals("discovery channel store", actual.get("53"));
        Assert.assertEquals("president of the united states", actual.get("54"));
    }

    @Test
    public void testParsingOfQueriesInRecursiveDirectory() {
        Map<String, String> actual = App.parseQueries("src/test/resources/cw09-input-with-cache");
        
        Assert.assertEquals("horse hooves", actual.get("51"));
        Assert.assertEquals("avp", actual.get("52"));
        Assert.assertEquals("discovery channel store", actual.get("53"));
        Assert.assertEquals("president of the united states", actual.get("54"));
    }

    @Test
    public void testParsingOfCacheForNonExistingCache() {
        Map<String, Long> actual = App.parseNetspeakCache("src/test/resources/cw09-input-without-cache");
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void testParsingOfCacheForExistingCache() {
        Map<String, Long> actual = App.parseNetspeakCache("src/test/resources/cw09-input-with-cache");
        Assert.assertEquals(Long.valueOf(1), Long.valueOf(actual.get("a")));
        Assert.assertEquals(Long.valueOf(2), Long.valueOf(actual.get("b")));
        Assert.assertEquals(Long.valueOf(3), Long.valueOf(actual.get("c")));
        Assert.assertEquals(Long.valueOf(1), Long.valueOf(actual.get("d")));
    }

        @Test
    public void testParsingAndWritingOfCache() {
        Map<String, Long> input = new LinkedHashMap<>();
        input.put("a", 1010l);
        input.put("b", 2020l);
        input.put("c", 3030l);
        input.put("d", 1l);
        
        App.persistCache("src/test/resources/tmp", input);

        Map<String, Long> actual = App.parseNetspeakCache("src/test/resources/tmp");
        Assert.assertEquals(Long.valueOf(1010), Long.valueOf(actual.get("a")));
        Assert.assertEquals(Long.valueOf(2020), Long.valueOf(actual.get("b")));
        Assert.assertEquals(Long.valueOf(3030), Long.valueOf(actual.get("c")));
        Assert.assertEquals(Long.valueOf(1), Long.valueOf(actual.get("d")));
    }
}
