package de.webis.query.segmentation.strategies;

import static de.webis.query.segmentation.core.QueryHelper.getSegmentLength;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.QueryHelper;
import de.webis.query.segmentation.core.QuerySegmentizer;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.utils.NgramHelper;

/**
 * Implementation of query segmentation strategy "naive" described in stein2010.
 */
public class StrategyNaive extends SegmentationStrategy {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StrategyNaive.class);

	protected String identifier = "naive";
	
	public StrategyNaive() {
		super();
	}
	
	public StrategyNaive(NgramHelper ngramHelper){
		super(ngramHelper);
	}
	
	
	@Override
	public Segmentation performSegmentation(Query query) {

		List<Segmentation> possibleSegmentation = QuerySegmentizer
				.getPossibleSegmentation(query);

		int maxScore = 0;
		int score = 0;
		Segmentation bestSegmentation = QueryHelper
				.getTrivialSegmentation(query);
		for (Segmentation segmentation : possibleSegmentation) {
			LOGGER.debug("Processing segmentation: "
					+ segmentation.toStringSegments());
			score = getScore(segmentation);
			if (score > maxScore) {
				bestSegmentation = segmentation;
				maxScore = score;
			}
		}
		return bestSegmentation;
	}

	/**
	 * Computes the score of a segmentation.
	 * 
	 * @param segmentation
	 * @return score
	 */
	private int getScore(Segmentation segmentation) {
		int score = 0;
		long ngramCount = 0;
		for (String segment : segmentation.getSegments()) {
			int len = getSegmentLength(segment);
			if (len >= 2) {
				ngramCount = ngramHelper.getNgramCount(segment);
				if (ngramCount == -1) {
					break;
				} else {
					score += (int) (Math.pow(len, len) * ngramCount);
				}
			}
		}
		return score;
	}
	

	@Override
	public String getIdentifier() {
		return StrategyIdentifiers.NAIVE;
	}

}
