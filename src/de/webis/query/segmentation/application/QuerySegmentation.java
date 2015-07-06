package de.webis.query.segmentation.application;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.QueryHelper;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.strategies.SegmentationStrategy;
import de.webis.query.segmentation.utils.NgramHelper;

/**
 * This is the main class of the query segmentation application.
 * 
 * @author Anna Beyer
 *
 */
public class QuerySegmentation {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuerySegmentation.class);
    private SegmentationStrategy segmentationStrategy;

    
    public QuerySegmentation(SegmentationStrategy segmentationAlgorithm) {
        super();
        this.segmentationStrategy = segmentationAlgorithm;
    }
    
    public List<Segmentation> performSegmentation(List<Query> queries){
        List<Segmentation> segmentations = new ArrayList<Segmentation>();
        for (Query query : queries) {
            segmentations.add(this.performSegmentation(query));
        }
        return segmentations;
    }
    
    
    public Segmentation performSegmentation(Query query){
    	Query cleaned  = new Query(query.getId(),QueryHelper.clearQuery(query.getQueryString()));
        Segmentation segmentation = QueryHelper.getTrivialSegmentation(cleaned);
        LOGGER.debug("Processing query: " + query);
    	if(QueryHelper.getSegmentLength(cleaned.getQueryString()) >= 3){
    		// perform segmentation for queries with minimum length 3
    		segmentation = this.segmentationStrategy.performSegmentation(cleaned);
        }
    	
    	return segmentation;
    }
    
    public String getStrategyName() {
        return this.segmentationStrategy.getIdentifier();
    }

    public NgramHelper getNGramHelper(){
    	return this.segmentationStrategy.getNgramHelper();
    }

}
