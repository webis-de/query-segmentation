package de.webis.query.segmentation.strategies;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.QueryHelper;
import de.webis.query.segmentation.core.Segmentation;

public class StrategyNone extends SegmentationStrategy {

	public StrategyNone() {
		super();
	}

	@Override
	public Segmentation performSegmentation(Query query) {
		return QueryHelper.getTrivialSegmentation(query);
	}

}
