package de.webis.query.segmentation.strategies;

import static de.webis.query.segmentation.core.QueryHelper.getSegmentLength;

import java.util.List;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.QuerySegmentizer;
import de.webis.query.segmentation.core.Segmentation;

public class StrategyNaive implements ISegmentationStrategy {

    @Override
    public Segmentation performSegmentation(Query query) {
        
        List<Segmentation> possibleSegmentation = QuerySegmentizer.getPossibleSegmentation(query);
        
        int maxScore = -1;
        Segmentation bestSegmentation = null;
        for (Segmentation segmentation : possibleSegmentation) {
            if(getScore(segmentation) >= maxScore){
                bestSegmentation = segmentation;
            }
        }
        return bestSegmentation;
    }
    
    private int getScore(Segmentation segmentation){
        int score = 0;
        for (String segment : segmentation.getSegments()) {
            int len = getSegmentLength(segment);
            if(len >= 2){
                int ngram = 1; //TODO
                score+= (int) (Math.pow(len, len) * ngram);
            }
        }
        return score;
    }
    

}
