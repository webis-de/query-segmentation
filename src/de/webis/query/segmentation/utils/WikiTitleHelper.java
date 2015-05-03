package de.webis.query.segmentation.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiTitleHelper {

	private static Logger LOGGER = LoggerFactory.getLogger(NgramHelper.class);
	private static String WIKI_TITLE_FILE = "./data/wikititle-sample.txt";

	private static Set<String> WIKI_TITLE;

	static {
		try {
			WIKI_TITLE = new HashSet<String>(FileUtils.readLines(new File(
					WIKI_TITLE_FILE)));
		} catch (IOException e) {
			LOGGER.error("Error while initializing wiki titles from: "
					+ WIKI_TITLE_FILE);
			WIKI_TITLE = new HashSet<String>();
		}
	}

	public static boolean isWikiTitle(String text) {
		return WIKI_TITLE.contains(text);
	}

}
