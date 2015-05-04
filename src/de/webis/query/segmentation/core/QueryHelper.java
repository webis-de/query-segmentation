package de.webis.query.segmentation.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
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
     */
    public static List<Query> readQueriesFromFile(String queriesFilePath)
            throws IOException {
        List<String> lines = FileUtils.readLines(new File(queriesFilePath));
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
    
    public static int getSegmentLength(String segment){
        return segment.split(" ").length;
    }
    
    public static Segmentation getTrivialSegmentation(Query query){
    	return new Segmentation(query.getId(), query.getQueryString().split(" "));
    }

}
