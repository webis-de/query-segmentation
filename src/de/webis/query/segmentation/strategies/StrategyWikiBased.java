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
import de.webis.query.segmentation.utils.WikiTitleHelper;

/**
 * Implementation of query segmentation strategy "wiki-based" described in
 * stein2011e.
 */
public class StrategyWikiBased extends SegmentationStrategy {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StrategyWikiBased.class);

	public StrategyWikiBased() {
		super();
	}

	public StrategyWikiBased(NgramHelper ngramHelper) {
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
		long weight = 0;
		for (String segment : segmentation.getSegments()) {
			int len = getSegmentLength(segment);
			if (len >= 2) {
				ngramCount = this.ngramHelper.getNgramCount(segment);
				if (ngramCount == -1) {
					break;
				} else {
					weight = getWeight(segment, ngramCount);
					score += len * weight;
				}
			}
		}
		return score;
	}

	/**
	 * Computes the weight of a segment.
	 * 
	 * @param segment
	 * @return
	 */
	private long getWeight(String segment, long ngramCount) {
		long weight = 0;
		int len = QueryHelper.getSegmentLength(segment);
		if (WikiTitleHelper.isWikiTitle(segment)) {
			weight = len + this.ngramHelper.getNgramCountOfSubTwoGram(segment);
		} else {
			weight = ngramCount;
		}
		return weight;
	}

}
