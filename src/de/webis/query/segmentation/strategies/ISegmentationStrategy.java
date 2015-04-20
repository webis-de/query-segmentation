package de.webis.query.segmentation.strategies;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.Segmentation;

public interface ISegmentationStrategy {
    
    public Segmentation performSegmentation(Query query);

}
