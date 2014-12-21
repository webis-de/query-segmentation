package de.webis.query.segmentation.strategies;

import java.util.List;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.Segmentation;

public interface ISegmentationStrategy {
    
    public List<Segmentation> performSegmentation(List<Query> queries);

}
