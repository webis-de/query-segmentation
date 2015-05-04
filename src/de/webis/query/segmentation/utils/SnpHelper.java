package de.webis.query.segmentation.utils;

import java.io.FileNotFoundException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import de.aitools.ie.postagging.PosTagger;

public class SnpHelper {

	private static PosTagger TAGGER;
	private static final Logger LOGGER = LoggerFactory.getLogger(SnpHelper.class);

	static {
		try {
			TAGGER = new PosTagger(Locale.ENGLISH);
		} catch (FileNotFoundException e) {
			TAGGER = null;
			e.printStackTrace();
		}
	}

	public static boolean isSnp(String query) {
		String[] tokens = TAGGER.tag(query.split(" "));
		boolean isSnp = true;
		for (String tag : tokens) {
			if(!tag.startsWith("N") && !tag.startsWith("J") && !tag.startsWith("D")){
				if(!tag.equals("CD")){
					isSnp = false;
					break;
				}
			}
		}
		LOGGER.debug(query + "\t" + Lists.newArrayList(tokens).toString());
		return isSnp;
	}

}
