package de.webis.query.segmentation.cli;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class EndToEndTest {
    @Test
    public void testWithoutCache() throws Exception {
        List<String> expected = Arrays.asList(
            "{\"qid\":\"55\",\"originalQuery\":\"iron\",\"segmentationApproach\":\"hyb-a\",\"segmentation\":[\"iron\"]}", 
            "{\"qid\":\"51\",\"originalQuery\":\"horse hooves\",\"segmentationApproach\":\"hyb-a\",\"segmentation\":[\"horse\",\"hooves\"]}",
            "{\"qid\":\"52\",\"originalQuery\":\"avp\",\"segmentationApproach\":\"hyb-a\",\"segmentation\":[\"avp\"]}",
            "{\"qid\":\"53\",\"originalQuery\":\"discovery channel store\",\"segmentationApproach\":\"hyb-a\",\"segmentation\":[\"discovery channel\",\"store\"]}",
            "{\"qid\":\"54\",\"originalQuery\":\"president of the united states\",\"segmentationApproach\":\"hyb-a\",\"segmentation\":[\"president of\",\"the united states\"]}");
        
        List<String> actual = runSegmentation("hyb-a", "src/test/resources/cw09-input-without-cache");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testWithoutCache2() throws Exception {
        List<String> expected = Arrays.asList(
            "{\"qid\":\"q062213307\",\"originalQuery\":\"leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg leg\",\"segmentationApproach\":\"hyb-a\",\"segmentation\":[\"leg leg\",\"leg leg\",\"leg\"]}", 
            "{\"qid\":\"52\",\"originalQuery\":\"solar panel self-consumption\",\"segmentationApproach\":\"hyb-a\",\"segmentation\":[\"solar panel\",\"self-consumption\"]}");
        
        List<String> actual = runSegmentation("hyb-a", "src/test/resources/longeval-input-without-cache");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testWithCache() throws Exception {
        List<String> expected = Arrays.asList(
            "{\"qid\":\"55\",\"originalQuery\":\"iron\",\"segmentationApproach\":\"hyb-a\",\"segmentation\":[\"iron\"]}", 
            "{\"qid\":\"51\",\"originalQuery\":\"horse hooves\",\"segmentationApproach\":\"hyb-a\",\"segmentation\":[\"horse\",\"hooves\"]}",
            "{\"qid\":\"52\",\"originalQuery\":\"avp\",\"segmentationApproach\":\"hyb-a\",\"segmentation\":[\"avp\"]}",
            "{\"qid\":\"53\",\"originalQuery\":\"discovery channel store\",\"segmentationApproach\":\"hyb-a\",\"segmentation\":[\"discovery channel\",\"store\"]}",
            "{\"qid\":\"54\",\"originalQuery\":\"president of the united states\",\"segmentationApproach\":\"hyb-a\",\"segmentation\":[\"president of\",\"the united states\"]}");
        
        List<String> actual = runSegmentation("hyb-a", "src/test/resources/cw09-input-with-cache");

        Assert.assertEquals(expected, actual);
    }

    private List<String> runSegmentation(String approach, String input) throws Exception {
        String tmpDir = Files.createTempDirectory("tmpDirPrefix").toFile().getAbsolutePath();
        String[] args = new String[] {"--segmentation-approach", approach, "--input", input, "--cache", input, "--output", tmpDir};
        App.main(args);
        System.out.println(tmpDir);
        return Files.readAllLines(Paths.get(tmpDir + "/queries.jsonl"));
    }
}

