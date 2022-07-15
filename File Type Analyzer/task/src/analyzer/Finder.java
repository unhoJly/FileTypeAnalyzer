package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Finder {
    private final SearchStrategy strategy;

    public Finder(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean find(String fileName, String searchPattern) {
        return strategy.getResult(fileName, searchPattern);
    }

    public Runnable findingTask(String fileName, List<String> list) {
        return () -> {
                    try {
                        String fileContent = new String(Files.readAllBytes(Path.of(fileName)));

                        for (int i = list.size() - 1; i >=0; i--) {
                            String s = list.get(i).replaceAll("\"", "");
                            String searchPattern = s.split(";")[1];
                            String output = s.split(";")[2];

                            if (find(fileContent, searchPattern)) {
                                System.out.println(Path.of(fileName).getFileName().toString() + ": " + output);
                                break;
                            } else {
                                System.out.println(Path.of(fileName).getFileName().toString() + ": " + "Unknown file type");
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };
    }
}