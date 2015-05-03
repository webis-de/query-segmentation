package de.webis.query.segmentation.application;

import java.io.IOException;
import java.util.List;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.QueryHelper;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.strategies.StrategyNaive;

public class HowToUseQuerySegmentation {

    public static void main(String[] args) {

        QuerySegmentation qs = new QuerySegmentation(new StrategyNaive());

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