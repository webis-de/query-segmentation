package de.webis.query.segmentation.application;

import java.util.List;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.strategies.ISegmentationStrategy;

/**
 * 
 * @author Anna Beyer
 *
 */
public class QuerySegmentation {

    private ISegmentationStrategy segmentationStrategy;

    
    public QuerySegmentation(ISegmentationStrategy segmentationAlgorithm) {
        super();
        this.segmentationStrategy = segmentationAlgorithm;
    }
    
    public List<Segmentation> performSegmentation(List<Query> queries){
        return this.segmentationStrategy.performSegmentation(queries);
    }
    
    public String getStrategyName() {
        return this.segmentationStrategy.getClass().getSimpleName();
    }

    
}
