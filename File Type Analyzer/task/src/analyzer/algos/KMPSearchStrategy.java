package analyzer.algos;

import analyzer.SearchStrategy;

public class KMPSearchStrategy implements SearchStrategy {

    @Override
    public boolean getResult(String fileContent, String pattern) {
        int[] pF = prefixFunction(pattern);
        int pL = pattern.length();
        int fL = fileContent.length();
        int i = 0; //index for fileContent
        int j = 0; //index for pattern

        while (i < fL) {
            if (fileContent.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j == pL) {
                    return true;
                }
            } else if (pattern.charAt(j) != fileContent.charAt(i)) {
                if (j != 0) {
                    j = pF[j - 1];
                } else {
                    i = i + 1;
                }
            }
        }

        return false;
    }

    private int[] prefixFunction(String pattern) {
        int[] prefixFunc = new int[pattern.length()];
        int prefix = 0;

        for (int i = 1; i < pattern.length(); i++) {
            char suffix = pattern.charAt(i);

            while (prefix > 0 && pattern.charAt(prefix) != suffix) {
                prefix = prefixFunc[prefix - 1];
            }

            if (pattern.charAt(prefix) == suffix) {
                prefix++;
            }

            prefixFunc[i] = prefix;
        }

        return prefixFunc;
    }
}