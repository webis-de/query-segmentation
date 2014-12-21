package de.webis.query.segmentation.strategies;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.Segmentation;

public class StrategySingleWords implements ISegmentationStrategy{

    @Override
    public List<Segmentation> performSegmentation(List<Query> queries) {
        
        List<Segmentation> listOfSegmentation = new ArrayList<>();
        
        for(Query q: queries){
            // split query in words
            List<String> words = Lists.newArrayList(q.getQueryString().split(" "));
            
            // construct segmentation
            Segmentation segmentation = new Segmentation(q.getId(), words);
            
            // add segmentation to list
            listOfSegmentation.add(segmentation);
            
        }
        return listOfSegmentation;
    }

}
