package de.webis.query.segmentation.strategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.utils.SnpHelper;

/**
 * Implementation of query segmentation strategy "hybrid-ir-none-wt" described
 * in stein2012q as HYB-B.
 */
public class StrategyHybridIrNoneWt implements ISegmentationStrategy {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StrategyHybridIrNoneWt.class);

	private final StrategyNone none = new StrategyNone();
	private final StrategyWtBaseline wtBaseline = new StrategyWtBaseline();

	@Override
	public Segmentation performSegmentation(Query query) {
		Segmentation segmentation = null;

		if (SnpHelper.isSnp(query.getQueryString())) {
			LOGGER.debug("Processing SNP query: " + query.getQueryString());
			segmentation = none.performSegmentation(query);
		} else {
			segmentation = wtBaseline.performSegmentation(query);
			LOGGER.debug("Processing non SNP query: " + query.getQueryString());
		}

		return segmentation;
	}

}
