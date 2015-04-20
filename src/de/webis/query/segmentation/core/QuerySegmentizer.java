package de.webis.query.segmentation.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

public class QuerySegmentizer {

    /*
     * Get a list of all possible segmentations of a query.
     */
    public static List<Segmentation> getPossibleSegmentation(Query query){
        List<String> words = Lists.newArrayList(query.getQueryString().split(" "));
        int numberOfWords = words.size();
        int breaks = numberOfWords -1;
        int numberOfPossibleSegmentations = (int) (Math.pow(2, breaks) - 1);
        
        List<Segmentation> segmentations = new ArrayList<Segmentation>();
        for(int i = 0; i < numberOfPossibleSegmentations; i++){
            String bitmask = StringUtils.leftPad(Integer.toBinaryString(i), breaks, '0');
            segmentations.add(getSegmentationFromBitmask(query, bitmask));
        }
        return segmentations;
    }
    
    /*
     * Generate segmentation of query from bitmask.
     */
    private static Segmentation getSegmentationFromBitmask(Query query, String bitmask){
        String queryString = query.getQueryString();
        for (char bit : bitmask.toCharArray()) {
            if(String.valueOf(bit).equals("0")){
                // mark non-breaks with ~
                queryString = queryString.replaceFirst(" ", "~");
            }else{
                // mark breaks with #
                queryString = queryString.replaceFirst(" ", "#");
            }
        }
        
        // undo marking non-breaks
        queryString = queryString.replace("~", " ");
        String[] split = queryString.split("#");
        return new Segmentation(query.getId(), split);
    }
}
