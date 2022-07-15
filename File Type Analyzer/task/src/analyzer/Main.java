package analyzer;

import analyzer.algos.RKSearchStrategy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final String CURRENT_DIR = System.getProperty("user.dir") + File.separator;

    public static void main(String[] args) {
        List<String> list = null;
        String searchPatterns = args[1];
        String folder = args[0];

        try {
            list = Files.readAllLines(Paths.get(CURRENT_DIR + searchPatterns))
                    .stream().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExecutorService executor = Executors.newCachedThreadPool();

        for (Path fileName: getAllFilesFromFolder(folder)) {
            executor.submit(new Finder(new RKSearchStrategy())
                    .findingTask(fileName.toString(), list));
        }

        try {
            executor.awaitTermination(60, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<Path> getAllFilesFromFolder(String folder) {
        try (Stream<Path> walk = Files.walk(Paths.get(CURRENT_DIR + folder))) {
            return walk
                    .filter(Files::isRegularFile)
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}