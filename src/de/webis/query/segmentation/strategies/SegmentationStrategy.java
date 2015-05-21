package de.webis.query.segmentation.strategies;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.QueryHelper;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.utils.NgramHelper;

public abstract class SegmentationStrategy {

	protected NgramHelper ngramHelper;
	
	public abstract Segmentation performSegmentation(Query query);

	public SegmentationStrategy(){
		this(new NgramHelper());
	}
	
	public SegmentationStrategy(NgramHelper ngramHelper) {
		this.ngramHelper = ngramHelper;
	}
	
	
	public NgramHelper getNgramHelper() {
		return ngramHelper;
	}

	public void setNgramHelper(NgramHelper ngramHelper) {
		this.ngramHelper = ngramHelper;
	}

}
