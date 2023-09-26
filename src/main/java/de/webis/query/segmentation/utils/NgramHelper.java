package de.webis.query.segmentation.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.netspeak.NetspeakClient;
import org.netspeak.SearchResults;
import org.netspeak.SearchResults.Phrase;
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

	private NetspeakClient client = new NetspeakClient(new File("/maven-dependencies/netspeak-client"));
	
	private static final long N_GRAM_FREQUENCY_MEDIAN = 3461030;

	private static final Decomposer DECOMPOSER = Decomposers
			.newOpenNlpTokenizerME();
	private static final Chunker CHUNKER = Decomposers.newTokenNgramChunker(2,
			1);
	
	private Map<String, Long> ngramCountsCache;
	
	public NgramHelper(){
		ngramCountsCache = new HashMap<String, Long>();
	}
	

	public NgramHelper(Map<String, Long> ngramCountsCache2) {
		this.ngramCountsCache = new HashMap<String, Long>(ngramCountsCache2);
	}


	public Map<String, Long> getNgramCountsCache() {
		return ngramCountsCache;
	}


	public void setNgramCountsCache(Map<String, Long> ngramCountsCache) {
		this.ngramCountsCache = ngramCountsCache;
	}


	/**
	 * Gets the n-gram frequency of a query.
	 * @param query
	 * @return n-gram frequency
	 */
	public long getNgramCount(String query) {
		long count = -1;
		if(ngramCountsCache.containsKey(query)){
			count = ngramCountsCache.get(query);
			LOGGER.debug("Get ngram count for \"" + query + "\" from local ngram cache.");
		}else{
			count = getNgramCountFromNetspeak(query);
			ngramCountsCache.put(query, count);
			LOGGER.debug("Get ngram count for \"" + query + "\" from Netspeak.");
		}
		
		return count;
	}

	public synchronized long getNgramCountFromNetspeak(String query) {
		try {
			SearchResults results = client.search(query);

			if (results.getPhrases() != null && results.getPhrases().size() > 0) {
				return results.getPhrases().get(0).getFrequency();
			} else {
				LOGGER.debug("Netspeak request: " + query + " ---> not in");
				return -1;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the highest n-gram frequency of any 2-grams of a segment
	 * 
	 * @param segment
	 * @return n-gram frequency
	 */
	public long getNgramCountOfSubTwoGram(String segment) {
		long maxTwoGramCount = 0;
		long twoGramCount = 0;
		String[] twoGrams = CHUNKER.chunk(DECOMPOSER.toStrings(segment));
		for (String twoGram : twoGrams) {
			if (QueryHelper.getSegmentLength(twoGram) == 2) {
				twoGramCount = this.getNgramCount(twoGram);
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
