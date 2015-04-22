package de.webis.query.segmentation.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.QueryHelper;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.strategies.StrategyNaive;

public class QuerySegmentationTest {

	private static List<Query> queriesTrainingSet;
	private static List<Query> queries;

	@BeforeClass
	public static void setUp() throws IOException {
		queriesTrainingSet = QueryHelper
				.readQueriesFromFile("./data/webis-qsec-10-training-set-queries.txt");

		queries = new ArrayList<Query>();
		queries.add(new Query(1004073900, "graffiti fonts alphabet"));
		queries.add(new Query(1004593125, "stainless steel chest freezers"));
		queries.add(new Query(1012650194, "how to make a bed"));
	}

	@Test
	public void testStrategyNaive() {
		QuerySegmentation qs = new QuerySegmentation(new StrategyNaive());
		List<Segmentation> segmentations = qs.performSegmentation(queries);
		QueryHelper.printSegmentations(segmentations);
		Assert.assertEquals("graffiti fonts|alphabet", segmentations.get(0)
				.toStringSegments());
		Assert.assertEquals("stainless steel|chest freezers", segmentations
				.get(1).toStringSegments());
		Assert.assertEquals("how to|make a|bed", segmentations.get(2)
				.toStringSegments());

	}

	@Test
	public void testStrategyNaive2() {
		QuerySegmentation qs = new QuerySegmentation(new StrategyNaive());
		List<Segmentation> segmentations = qs
				.performSegmentation(queriesTrainingSet.subList(0, 10));
		QueryHelper.printSegmentations(segmentations);
	}

}
