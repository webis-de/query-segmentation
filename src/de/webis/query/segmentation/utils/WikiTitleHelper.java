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
	private static final String DATA_DIR = "/media/storage1/data-in-progress/query-segmentation/data/";
	private static final String WIKI_TITLE_FILE = DATA_DIR  +"wikititle.txt";
	private static final String STOP_WIKI_TITLE_FILE = DATA_DIR + "stop-wikititle.txt";

	private static Set<String> WIKI_TITLE;

	static {
		try {
			WIKI_TITLE = new HashSet<String>(FileUtils.readLines(new File(
					WIKI_TITLE_FILE)));
			
			Set<String> stopp_wikititle = new HashSet<String>(FileUtils.readLines(new File(
					STOP_WIKI_TITLE_FILE)));
			
			WIKI_TITLE.removeAll(stopp_wikititle);
		} catch (IOException e) {
			String msg = "Failed to load wiki titles from file: "
					+ WIKI_TITLE_FILE;
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
	}

	public static boolean isWikiTitle(String text) {
		return WIKI_TITLE.contains(text);
	}

}
