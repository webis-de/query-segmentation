package de.webis.query.segmentation.utils;

import java.io.IOException;

import org.netspeak.application.ErrorCode;
import org.netspeak.application.generated.NetspeakMessages.Response;
import org.netspeak.client.Netspeak;
import org.netspeak.client.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.aitools.ie.decomposition.Chunker;
import de.aitools.ie.decomposition.Decomposer;
import de.aitools.ie.decomposition.Decomposers;
import de.webis.query.segmentation.core.QueryHelper;

/**
 * Helper class for getting n-gram frequencies using the Netspeak api.
 * 
 */
public class NgramHelper {

	private static Logger LOGGER = LoggerFactory.getLogger(NgramHelper.class);

	private static final Netspeak NETSPEAK;
	
	private static final long N_GRAM_FREQUENCY_MEDIAN = 3461030;

	private static final Decomposer DECOMPOSER = Decomposers
			.newOpenNlpTokenizerME();
	private static final Chunker CHUNKER = Decomposers.newTokenNgramChunker(2,
			1);

	static {
		NETSPEAK = new Netspeak();
	}

	/**
	 * Gets the n-gram frequency of a query.
	 * @param query
	 * @return n-gram frequency
	 */
	public static long getNgramCount(String query) {
		long count = -1;

		Request request = new Request();
		request.put(Request.QUERY, query);

		LOGGER.debug("Netspeak request: " + query);

		try {
			Response response = NETSPEAK.search(request);

			ErrorCode errorCode = ErrorCode.cast(response.getErrorCode());
			if (errorCode != ErrorCode.NO_ERROR) {
				System.err.println("Error code: " + errorCode);
				System.err.println("Error message: "
						+ response.getErrorMessage());
				LOGGER.debug("Error while Netspeak request: " + query);
			}

			if (response.getPhraseCount() > 0) {
				count = response.getPhrase(0).getFrequency();
			} else {
				LOGGER.debug("Netspeak request: " + query + " ---> not in");
			}
		} catch (IOException e) {
			LOGGER.debug("Error while Netspeak request: " + query, e);
		}
		return count;
	}

	/**
	 * Gets the highest n-gram frequency of any 2-grams of a segment
	 * 
	 * @param segment
	 * @return n-gram frequency
	 */
	public static long getNgramCountOfSubTwoGram(String segment) {
		long maxTwoGramCount = 0;
		long twoGramCount = 0;
		String[] twoGrams = CHUNKER.chunk(DECOMPOSER.toStrings(segment));
		for (String twoGram : twoGrams) {
			if (QueryHelper.getSegmentLength(twoGram) == 2) {
				twoGramCount = NgramHelper.getNgramCount(twoGram);
				if (twoGramCount > maxTwoGramCount) {
					maxTwoGramCount = twoGramCount;
				}
			}
		}
		if(maxTwoGramCount == 0){
			maxTwoGramCount = N_GRAM_FREQUENCY_MEDIAN;
		}
		return maxTwoGramCount;
	}

}
