package de.webis.query.segmentation.application;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.QueryHelper;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.strategies.StrategyHybridAcc;
import de.webis.query.segmentation.strategies.StrategyHybridIrNoneWikiBased;
import de.webis.query.segmentation.strategies.StrategyHybridIrNoneWt;
import de.webis.query.segmentation.strategies.StrategyNaive;
import de.webis.query.segmentation.strategies.StrategyWikiBased;
import de.webis.query.segmentation.strategies.StrategyWtBaseline;
import de.webis.query.segmentation.strategies.StrategyWtSnpBaseline;
import de.webis.query.segmentation.utils.NgramHelper;

public class QuerySegmentationTest {

	private static List<Query> queriesTrainingSet;

	@BeforeClass
	public static void setUp() throws IOException {
		queriesTrainingSet = QueryHelper.readQueriesFromFile(
				"./data/webis-qsec-10-training-set-queries.txt").subList(0, 10);
	}

	@Test
	public void testStrategyNaive() {
		QuerySegmentation qs = new QuerySegmentation(new StrategyNaive());
		List<Segmentation> segmentations = qs
				.performSegmentation(queriesTrainingSet);
		QueryHelper.printSegmentations(segmentations);
	}

	@Test
	public void testStrategyWikiBased() {
		QuerySegmentation qs = new QuerySegmentation(new StrategyWikiBased());
		List<Segmentation> segmentations = qs
				.performSegmentation(queriesTrainingSet);
		QueryHelper.printSegmentations(segmentations);
	}

	@Test
	public void testStrategyWtBaseline() {
		QuerySegmentation qs = new QuerySegmentation(new StrategyWtBaseline());
		List<Segmentation> segmentations = qs
				.performSegmentation(queriesTrainingSet);
		QueryHelper.printSegmentations(segmentations);
	}

	@Test
	public void testStrategyWtSnpBaseline() {
		QuerySegmentation qs = new QuerySegmentation(
				new StrategyWtSnpBaseline());
		List<Segmentation> segmentations = qs
				.performSegmentation(queriesTrainingSet);
		QueryHelper.printSegmentations(segmentations);
	}

	@Test
	public void testStrategyHybridAcc() {
		QuerySegmentation qs = new QuerySegmentation(new StrategyHybridAcc());
		List<Segmentation> segmentations = qs
				.performSegmentation(queriesTrainingSet);
		QueryHelper.printSegmentations(segmentations);
	}

	@Test
	public void testStrategyHybridIrNoneWt() {
		QuerySegmentation qs = new QuerySegmentation(
				new StrategyHybridIrNoneWt());
		List<Segmentation> segmentations = qs
				.performSegmentation(queriesTrainingSet);
		QueryHelper.printSegmentations(segmentations);
	}

	@Test
	public void testStrategyHybridIrNoneWikiBased() {
		QuerySegmentation qs = new QuerySegmentation(
				new StrategyHybridIrNoneWikiBased());
		List<Segmentation> segmentations = qs
				.performSegmentation(queriesTrainingSet);
		QueryHelper.printSegmentations(segmentations);
	}
	
	@Test
	public void testNgramHelperForSeveralSegmentationStrategiesInSeries(){
		
		NgramHelper ngramHelper = new NgramHelper();
		
		QuerySegmentation qs1 = new QuerySegmentation(new StrategyNaive(ngramHelper));
		qs1.performSegmentation(new Query("how to make a bed"));
		
		ngramHelper = qs1.getNGramHelper();
		int numberCachedNgrams = ngramHelper.getNgramCountsCache().size();
		Assert.assertTrue(0< numberCachedNgrams);
		
		QuerySegmentation qs2 = new QuerySegmentation(new StrategyWikiBased(ngramHelper));
		qs1.performSegmentation(new Query("stainless steel chest freezers"));
		ngramHelper = qs2.getNGramHelper();
		int numberCachedNGrams2 = ngramHelper.getNgramCountsCache().size();
		
		Assert.assertTrue(0< numberCachedNGrams2);
		Assert.assertTrue(numberCachedNgrams < numberCachedNGrams2);
	}
	
	@Test
	public void testNgramHelperForSeveralSegmentationStrategiesInSeries2(){
	NgramHelper ngramHelper = new NgramHelper();
	
	QuerySegmentation qs1 = new QuerySegmentation(new StrategyNaive(ngramHelper));
	QuerySegmentation qs2 = new QuerySegmentation(new StrategyWikiBased(ngramHelper));
	
	qs1.performSegmentation(new Query("how to make a bed"));
	int count1 = ngramHelper.getNgramCountsCache().size();
	Assert.assertTrue(0<count1);
	
	qs2.performSegmentation(new Query("stainless steel chest freezers"));
	int count2 = ngramHelper.getNgramCountsCache().size();
	
	Assert.assertTrue(0<count2);
	Assert.assertTrue(count1<count2);
	
	}
}
