package de.webis.query.segmentation.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class QueryHelper {

	/**
	 * Replaces unwanted special characters in string with whitespace, and trims
	 * subsequent whitespace down to one whitespace.
	 * 
	 * @param query
	 * @return
	 */
	public static String clearQuery(String query) {
		String queryProcessed = query.replaceAll("[^A-Za-z0-9' .-]", " ");
		return queryProcessed.trim().replaceAll(" +", " ").toLowerCase();
	}

	/**
	 * Reads queries from file.
	 * 
	 * @throws Exception
	 */
	public static List<Query> readQueriesFromFile(String queriesFilePath)
			throws Exception {
		List<String> lines = FileUtils.readLines(new File(queriesFilePath));
		return readQueriesFromLines(lines);
	}

	/**
	 * Reads queries from list of lines
	 * 
	 * @param lines
	 *            the lines containing a query.
	 * @return list of {@link Query}
	 */
	public static List<Query> readQueriesFromLines(List<String> lines)
			throws Exception {
		List<Query> queries = new ArrayList<>();
		for (String line : lines) {
			String[] splitted = StringUtils.split(line, "\t");
			Long id = Long.valueOf(splitted[0]);
			String query = splitted[1];
			queries.add(new Query(id, query));

		}
		return queries;
	}

	/**
	 * Prints a list of segmentations to stdout.
	 * 
	 * @param listOfSegmentations
	 */
	public static void printSegmentations(List<Segmentation> listOfSegmentations) {
		for (Segmentation s : listOfSegmentations) {
			System.out.println(s.toString());
		}
	}

	/**
	 * Get string representation of segmentations.
	 * 
	 * @param listOfSegmentations
	 *            list of segmentations
	 * @return list of strings
	 */
	public static List<String> getSegmentationsAsStrings(
			List<Segmentation> listOfSegmentations) {
		List<String> strings = new ArrayList<String>();
		for (Segmentation s : listOfSegmentations) {
			strings.add(s.toString());
		}
		return strings;
	}

	public static String getSegmentationsAsString(
			List<Segmentation> listOfSegmentations) {
		List<String> segmentationsAsStrings = getSegmentationsAsStrings(listOfSegmentations);
		return StringUtils.join(segmentationsAsStrings, "\n");
	}

	public static int getSegmentLength(String segment) {
		return segment.split(" ").length;
	}

	public static Segmentation getTrivialSegmentation(Query query) {
		return new Segmentation(query.getId(), query.getQueryString()
				.split(" "));
	}

}
