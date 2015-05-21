package de.webis.query.segmentation.strategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.utils.NgramHelper;
import de.webis.query.segmentation.utils.SnpHelper;

/**
 * Implementation of query segmentation strategy "hybrid-ir-none-wt" described
 * in stein2012q as HYB-B.
 */
public class StrategyHybridIrNoneWt extends SegmentationStrategy {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StrategyHybridIrNoneWt.class);

	private StrategyNone none;
	private StrategyWtBaseline wtBaseline;
	
	public StrategyHybridIrNoneWt() {
		super();
		this.none = new StrategyNone();
		this.wtBaseline = new StrategyWtBaseline();
	}
	
	public StrategyHybridIrNoneWt(NgramHelper ngramHelper) {
		super(ngramHelper);
		this.none = new StrategyNone();
		this.wtBaseline = new StrategyWtBaseline(ngramHelper);
	}

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
