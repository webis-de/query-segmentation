package de.webis.query.segmentation.utils;

import java.io.IOException;

import org.netspeak.application.ErrorCode;
import org.netspeak.application.generated.NetspeakMessages.Response;
import org.netspeak.client.Netspeak;
import org.netspeak.client.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to obtain n-gram counts using the Netspeak api.
 * 
 * @author Anna Beyer
 *
 */
public class NgramHelper {

	private static Logger LOGGER = LoggerFactory.getLogger(NgramHelper.class);

	private static final Netspeak NETSPEAK;
	static {
		NETSPEAK = new Netspeak();
	}

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

}
