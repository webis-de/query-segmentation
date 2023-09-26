package de.webis.query.segmentation.strategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.utils.NgramHelper;
import de.webis.query.segmentation.utils.SnpHelper;

/**
 * Implementation of query segmentation strategy "hybrid-acc" described in
 * stein2012q as HYB-A.
 */
public class StrategyHybridA extends SegmentationStrategy {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StrategyHybridA.class);
	
	private StrategyWikiBased wikiBased;
	private StrategyWtBaseline wtBaseline;

	public StrategyHybridA() {
		super();
		this.wikiBased = new StrategyWikiBased();
		this.wtBaseline = new StrategyWtBaseline();
	}

	public StrategyHybridA(NgramHelper ngramHelper) {
		super(ngramHelper);
		this.wikiBased = new StrategyWikiBased(ngramHelper);
		this.wtBaseline = new StrategyWtBaseline(ngramHelper);
	}

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

	@Override
	public String getIdentifier() {
		return StrategyIdentifiers.HYB_A;
	}
}
