package de.webis.query.segmentation.strategies;

import static de.webis.query.segmentation.core.QueryHelper.getSegmentLength;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.QuerySegmentizer;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.utils.NgramHelper;

public class StrategyNaive implements ISegmentationStrategy {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StrategyNaive.class);

	@Override
	public Segmentation performSegmentation(Query query) {

		List<Segmentation> possibleSegmentation = QuerySegmentizer
				.getPossibleSegmentation(query);

		int maxScore = -1;
		int score = -1;
		Segmentation bestSegmentation = null;
		for (Segmentation segmentation : possibleSegmentation) {
			LOGGER.debug("Processing segmentation: " + segmentation.toStringSegments());
			score = getScore(segmentation);
			if (score > maxScore) {
				bestSegmentation = segmentation;
				maxScore = score;
			}
		}
		return bestSegmentation;
	}

	private int getScore(Segmentation segmentation) {
		int score = 0;
		long ngramCount = 0;
		for (String segment : segmentation.getSegments()) {
			int len = getSegmentLength(segment);
			if (len >= 2) {
				ngramCount = NgramHelper.getNgramCount(segment);
				if (ngramCount == -1) {
					break;
				} else {
					score += (int) (Math.pow(len, len) * ngramCount);
				}
			}
		}
		return score;
	}

}
