package de.webis.query.segmentation.core;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class QuerySegmentizerTest {

    @Test
    public void testGetAllPossibleSegmentation() {
        
        Query query =  new Query(1L,"this is a test query");
        List<Segmentation> allPossibleSegmentation = QuerySegmentizer.getPossibleSegmentation(query);
        
        Assert.assertEquals(15, allPossibleSegmentation.size());
        QueryHelper.printSegmentations(allPossibleSegmentation);
        
    }

}
