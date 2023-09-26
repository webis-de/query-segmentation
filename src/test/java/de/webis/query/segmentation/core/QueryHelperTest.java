package de.webis.query.segmentation.core;

import org.junit.Assert;
import org.junit.Test;


public class QueryHelperTest {

    @Test
    public void testClearQuery() throws Exception {
        
        String query = "This is #anna's test query,#*! END";
        Assert.assertEquals("this is anna's test query end", QueryHelper.clearQuery(query));
        
    }

}
