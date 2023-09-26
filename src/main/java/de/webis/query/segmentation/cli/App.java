package de.webis.query.segmentation.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.webis.query.segmentation.application.QuerySegmentation;
import de.webis.query.segmentation.core.Query;
import de.webis.query.segmentation.core.Segmentation;
import de.webis.query.segmentation.strategies.StrategyHybridA;
import de.webis.query.segmentation.strategies.StrategyHybridI;
import de.webis.query.segmentation.strategies.StrategyHybridB;
import de.webis.query.segmentation.strategies.StrategyNaive;
import de.webis.query.segmentation.strategies.StrategyWikiBased;
import de.webis.query.segmentation.strategies.StrategyWtBaseline;
import de.webis.query.segmentation.strategies.StrategyWtSnpBaseline;
import de.webis.query.segmentation.utils.NgramHelper;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;


public class App {
    public static void main(String[] args) {
        try {
            Namespace parsedArgs = argParser().parseArgs(args);
            String input = parsedArgs.getString("input");
            String output = parsedArgs.getString("output");
            NgramHelper ngramHelper = new NgramHelper(parseNetspeakCache(parsedArgs.get("cache")));

            runQuerySegmentation(parsedArgs.getList("segmentation_approach"), ngramHelper, input, output);

            persistCache(output, ngramHelper.getNgramCountsCache());
        } catch (ArgumentParserException e) {
            argParser().handleError(e);
            System.exit(1);
            return;
        }
    }

    private static void runQuerySegmentation(List<String> querySegmentationArg, NgramHelper ngramHelper, String inputDirectory, String outputDirectory) {
        List<QuerySegmentation> querySegmentations = new ArrayList<>();

        for (String i: querySegmentationArg) {
            QuerySegmentation querySegmentation = querySegmentations(ngramHelper).get(i);
            if (querySegmentation == null) {
                System.out.println("I could not find a query segmentation approach with name '" + i + "'. I will exit");
                System.exit(1);
                return;
            }

            querySegmentations.add(querySegmentation);
        }

        runQuerySegmentation(querySegmentations, inputDirectory, outputDirectory);
    }

    private static void runQuerySegmentation(List<QuerySegmentation> querySegmentations, String inputDirectory, String outputDirectory) {
        Map<String, String> queries = parseQueries(inputDirectory);
        List<String> ret = new ArrayList<>();
        
        for (QuerySegmentation querySegmentation: querySegmentations) {
            for(Map.Entry<String, String> qidToQuery: queries.entrySet()) {
                Map<String, Object> i = new LinkedHashMap<>();
                i.put("qid", qidToQuery.getKey());
                i.put("originalQuery", qidToQuery.getValue());
                i.put("segmentationApproach", querySegmentation.getStrategyName());
                Segmentation s = querySegmentation.performSegmentation(new Query(qidToQuery.getValue()));
                i.put("segmentation", s.getSegments());

                try {
                    ret.add(new ObjectMapper().writeValueAsString(i));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        try {
            Files.write(Paths.get(outputDirectory).resolve("queries.jsonl"), ret);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Map<String, QuerySegmentation> querySegmentations(NgramHelper ngramHelper) {
        return Arrays.asList(
                new StrategyNaive(ngramHelper), new StrategyWikiBased(ngramHelper),
                new StrategyWtBaseline(ngramHelper), new StrategyWtSnpBaseline(ngramHelper),
                new StrategyHybridA(ngramHelper), new StrategyHybridI(ngramHelper), 
                new StrategyHybridB(ngramHelper)
            )
            .stream().map(i -> new QuerySegmentation(i)).collect(Collectors.toMap(i -> i.getStrategyName(), i -> i));
	}

    private static ArgumentParser argParser() {
        ArgumentParser parser = ArgumentParsers.newFor("query-segmentation pipeline component").build()
                .defaultHelp(true)
                .description("Produce query Segmentations for queries in ir_datasets format");

        parser.addArgument("--segmentation-approach")
                .choices(querySegmentations(new NgramHelper()).keySet())
                .nargs("+")
                .required(true);

        parser.addArgument("--input")
                .help("The input directory")
                .required(true);
        
        parser.addArgument("--cache")
                .help("The cache directory")
                .required(true);

        parser.addArgument("--output")
                .help("The output directory")
                .required(true);

        return parser;
    }

    static Map<String, String> parseQueries(String inputDirectory) {
        return lines(findFileInDirectory(inputDirectory, "queries.jsonl")).stream()
            .map(i -> parseJson(i))
            .collect(Collectors.toMap(i -> (String) i.get("qid"), i -> (String) i.get("query")));
    }

    static void persistCache(String outputDirectory, Map<String, Long> cache) {
        Map<String, String> toPersist = cache.entrySet().stream().collect(Collectors.toMap(i -> i.getKey(), i -> "" + i.getValue()));
        try {
            new ObjectMapper().writeValue(Paths.get(outputDirectory).resolve("netspeak-cache.json").toFile(), toPersist);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Map<String, Long> parseNetspeakCache(String inputDirectory) {
        Path cacheFile = findFileInDirectory(inputDirectory, "netspeak-cache.json");
        if (cacheFile == null) {
            return new LinkedHashMap<>();
        }
        
        try {
            Map<String, String>  ret = new ObjectMapper().readValue(cacheFile.toFile(), Map.class);
            return ret.entrySet().stream().collect(Collectors.toMap(i -> i.getKey(), i -> Long.valueOf(i.getValue())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> lines(Path file) {
        try {
            return Files.readAllLines(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Object> parseJson(String json) {
        try {
            return new ObjectMapper().readValue(json, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path findFileInDirectory(String directory, String fileName) {
        Path directoryPath = Paths.get(directory);
        if (Files.exists(directoryPath.resolve(fileName))) {
            return directoryPath.resolve(fileName);
        }

        if (directoryPath == null || directoryPath.toFile() == null || directoryPath.toFile().list() == null) {
            return null;
        }

        for (String p: directoryPath.toFile().list()) {
            Path ret = findFileInDirectory(directoryPath.resolve(p).toAbsolutePath().toFile().getAbsolutePath(), fileName);
            if (ret != null) {
                return ret;
            }
        }

        return null;
    }
}
