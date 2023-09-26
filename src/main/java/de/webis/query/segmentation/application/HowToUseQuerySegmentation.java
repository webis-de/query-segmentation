package de.webis.query.segmentation.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.strategies.StrategyNaive;

public class HowToUseQuerySegmentation {

    public static void main(String[] args) {
    	Logger logger = LoggerFactory.getLogger(HowToUseQuerySegmentation.class);

    	// print internal state
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
        
        for(int i = 0; i < 10000; i++){
        logger.trace("test");
        logger.debug("test");
        logger.info("test");
        logger.warn("test arfsdxjhvbljkb lk klö lkniojoi");
        logger.error("test jhdtgdzrdzrdztdtzdz");
        }
    	
        QuerySegmentation qs = new QuerySegmentation(new StrategyNaive());
        qs.performSegmentation(new Query("short query"));

//        try {
//            List<Query> queries = QueryHelper.readQueriesFromFile("./data/webis-qsec-10-training-set-queries.txt");
//            
//            List<Segmentation> listOfSegmentations = qs.performSegmentation(queries);
//                    
//            QueryHelper.printSegmentations(listOfSegmentations);
//            
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
           
        
    }

}