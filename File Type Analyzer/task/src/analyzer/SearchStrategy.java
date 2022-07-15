package analyzer;

public interface SearchStrategy {
    boolean getResult(String fileContent, String searchPattern);
}