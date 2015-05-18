package de.webis.query.segmentation.application;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.QueryHelper;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.strategies.ISegmentationStrategy;

/**
 * This is the main class of the query segmentation application.
 * 
 * @author Anna Beyer
 *
 */
public class QuerySegmentation {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuerySegmentation.class);
    private ISegmentationStrategy segmentationStrategy;

    
    public QuerySegmentation(ISegmentationStrategy segmentationAlgorithm) {
        super();
        this.segmentationStrategy = segmentationAlgorithm;
    }
    
    public List<Segmentation> performSegmentation(List<Query> queries){
        List<Segmentation> segmentations = new ArrayList<Segmentation>();
        for (Query query : queries) {
            LOGGER.info("Processing query: " + query);
            segmentations.add(this.performSegmentation(query));
        }
        return segmentations;
    }
    
    
    public Segmentation performSegmentation(Query query){
    	Query cleaned  = new Query(query.getId(),QueryHelper.clearQuery(query.getQueryString()));
        Segmentation segmentation = QueryHelper.getTrivialSegmentation(cleaned);
    	if(QueryHelper.getSegmentLength(cleaned.getQueryString()) >= 3){
    		// perform segmentation for queries with minimum length 3
    		segmentation = this.segmentationStrategy.performSegmentation(cleaned);
        }
    	
    	return segmentation;
    }
    
    public String getStrategyName() {
        return this.segmentationStrategy.getClass().getSimpleName();
    }

    
//    stein2010j-naive.txt
//    stein2011e-wiki-based.txt
//    stein2012q-hybrid-acc.txt                          [HYB-A im paper]
//    stein2012q-hybrid-ir-none-stein11e.txt   [HYB-I im paper]
//    stein2012q-hybrid-ir-none-wt.txt              [HYB-B im paper]
//    stein2012q-wt-baseline.txt
//    stein2012q-wt-snp-baseline.txt 
    
}
