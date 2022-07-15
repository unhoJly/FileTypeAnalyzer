package analyzer.algos;

import analyzer.SearchStrategy;

public class RKSearchStrategy implements SearchStrategy {

    @Override
    public boolean getResult(String fileContent, String pattern) {
        int a = 117;
        long m = 173_961_102_589_771L;

        if (pattern.length() > fileContent.length()) {
            return false;
        }

        long patternHash = 0;
        long currSubstrHash = 0;
        long pow = 1;

        for (int i = 0; i < pattern.length(); i++) {
            patternHash += (long) pattern.charAt(i) * pow;
            patternHash %= m;

            currSubstrHash += (long) fileContent.charAt(fileContent.length() - pattern.length() + i) * pow;
            currSubstrHash %= m;

            if (i != pattern.length() - 1) {
                pow = pow * a % m;
            }
        }

        for (int i = fileContent.length(); i >= pattern.length(); i--) {
            if (patternHash == currSubstrHash) {
                for (int j = 0; j < pattern.length(); j++) {
                    if (fileContent.charAt(i - pattern.length() + j) != pattern.charAt(j)) {
                        break;
                    }
                }

                return true;
            }

            if (i > pattern.length()) {
                currSubstrHash = (currSubstrHash - fileContent.charAt(i - 1) * pow % m + m) * a % m;
                currSubstrHash = (currSubstrHash + fileContent.charAt(i - pattern.length() - 1)) % m;
            }
        }

        return false;
    }
}