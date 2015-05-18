package de.webis.query.segmentation.utils;

import java.io.FileNotFoundException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import de.aitools.ie.postagging.OpenNlpPosTaggerME;
import de.aitools.ie.postagging.PosTagger;

public class SnpHelper {

	private static PosTagger TAGGER;
	private static final Logger LOGGER = LoggerFactory.getLogger(SnpHelper.class);

	static {
		try {
			TAGGER = new OpenNlpPosTaggerME(Locale.ENGLISH);
		} catch (Exception e) {
			TAGGER = null;
			e.printStackTrace();
		}
	}

	public static boolean isSnp(String query) {
		String[][][] result = TAGGER.tag(query);
		boolean isSnp = true;
		for (int j = 0; j != result[0].length; ++j) {
			String[] token = result[0][j];
			String tag = token[1];
			if(!tag.startsWith("N") && !tag.startsWith("J") && !tag.startsWith("D")){
				if(!tag.equals("CD")){
					isSnp = false;
					break;
				}
			}
		}
		LOGGER.debug(query + "\t" + Lists.newArrayList(result).toString());
		return isSnp;
	}

}
