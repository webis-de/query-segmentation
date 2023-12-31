package de.webis.query.segmentation.strategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.utils.NgramHelper;
import de.webis.query.segmentation.utils.SnpHelper;

/**
 * Implementation of query segmentation strategy "hybrid-ir-none-stein11e" described
 * in stein2012q as HYB-I.
 */
public class StrategyHybridI extends SegmentationStrategy {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StrategyHybridI.class);

	protected String identifier = "hyb-i";
	
	private final StrategyNone none;
	private final StrategyWikiBased wikiBased;

	public StrategyHybridI() {
		super();
		this.none = new StrategyNone();
		this.wikiBased = new StrategyWikiBased();
	}
	
	public StrategyHybridI(NgramHelper ngramHelper){
		super(ngramHelper);
		this.none = new StrategyNone();
		this.wikiBased = new StrategyWikiBased(ngramHelper);
	
	}
	
	@Override
	public Segmentation performSegmentation(Query query) {
		Segmentation segmentation = null;

		if (SnpHelper.isSnp(query.getQueryString())) {
			LOGGER.debug("Processing SNP query: " + query.getQueryString());
			segmentation = none.performSegmentation(query);
		} else {
			segmentation = wikiBased.performSegmentation(query);
			LOGGER.debug("Processing non SNP query: " + query.getQueryString());
		}

		return segmentation;
	}
	

	@Override
	public String getIdentifier() {
		return StrategyIdentifiers.HYB_I;
	}

}
