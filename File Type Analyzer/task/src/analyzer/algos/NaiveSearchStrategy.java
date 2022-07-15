package analyzer.algos;

import analyzer.SearchStrategy;

public class NaiveSearchStrategy implements SearchStrategy {

    @Override
    public boolean getResult(String fileContent, String searchPattern) {
        return fileContent.contains(searchPattern);
    }
}