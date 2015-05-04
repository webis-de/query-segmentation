package de.webis.query.segmentation.strategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.utils.SnpHelper;

/**
 * Implementation of query segmentation strategy "hybrid-acc" described in
 * stein2012q as HYB-A.
 */
public class StrategyHybridAcc implements ISegmentationStrategy {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StrategyHybridAcc.class);

	private final StrategyWikiBased wikiBased = new StrategyWikiBased();
	private final StrategyWtBaseline wtBaseline = new StrategyWtBaseline();

	@Override
	public Segmentation performSegmentation(Query query) {
		Segmentation segmentation = null;

		if (SnpHelper.isSnp(query.getQueryString())) {
			LOGGER.debug("Processing SNP query: " + query.getQueryString());
			segmentation = wikiBased.performSegmentation(query);
		} else {
			segmentation = wtBaseline.performSegmentation(query);
			LOGGER.debug("Processing non SNP query: " + query.getQueryString());
		}
		return segmentation;
	}

}
