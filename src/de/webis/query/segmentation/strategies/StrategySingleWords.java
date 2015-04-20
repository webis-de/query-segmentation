package de.webis.query.segmentation.strategies;

import java.util.List;

import com.google.common.collect.Lists;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.Segmentation;

public class StrategySingleWords implements ISegmentationStrategy{

    @Override
    public Segmentation performSegmentation(Query query) {
        List<String> words = Lists.newArrayList(query.getQueryString().split(" "));
        Segmentation segmentation = new Segmentation(query.getId(), words);
        return segmentation;
    }

}
