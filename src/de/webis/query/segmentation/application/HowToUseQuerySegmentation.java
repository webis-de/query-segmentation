package de.webis.query.segmentation.application;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.QueryHelper;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.strategies.StrategySingleWords;

public class HowToUseQuerySegmentation {

    public static void main(String[] args) {

        QuerySegmentation qs = new QuerySegmentation(new StrategySingleWords());

//        List<Query> queries = Lists.newArrayList(new Query(1,"graffiti fonts alphabet"),
//                new Query(2, "stainless steel chest freezers"),
//                new Query(3, "rutgers online graduate classes"),
//                new Query(4, "review on breezes"));
//        
        
       
        try {
            List<Query> queries = QueryHelper.readQueriesFromFile("./data/webis-qsec-10-training-set-queries.txt");
            
            List<Segmentation> listOfSegmentations = qs.performSegmentation(queries);
                    
            QueryHelper.printSegmentations(listOfSegmentations);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
           
        
    }

}