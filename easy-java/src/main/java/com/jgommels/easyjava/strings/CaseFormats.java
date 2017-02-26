package com.jgommels.easyjava.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Provides static utility methods for getting the {@link CaseFormat} for strings and converting between different
 * {@link CaseFormat}s.
 */
public class CaseFormats {

    public static CaseFormat getCaseFormat(String str) {
        return CaseFormat.getCaseFormat(str);
    }

    public static String convertCase(String str, CaseFormat caseFormat) {
        return convertCaseFormat(str, getCaseFormat(str), caseFormat);
    }

    private static String convertCaseFormat(String str, CaseFormat sourceFormat, CaseFormat destinationFormat) {
        if(!destinationFormat.canBeConvertedTo()) {
            throw new IllegalArgumentException(destinationFormat.name() + " is not a valid target " + CaseFormat.class.getSimpleName());
        }

        return convert(str, sourceFormat, destinationFormat);
    }

    private static String convert(String str, CaseFormat sourceFormat, CaseFormat destinationFormat) {
        List<String> words = getWords(str, sourceFormat);
        StringBuilder result = new StringBuilder();

        for (int i=0; i<words.size(); i++) {
            boolean firstWord = i == 0;
            boolean lastWord = i == words.size() - 1;
            String convertedWord = destinationFormat.wordConverter.apply(words.get(i), firstWord);

            if (destinationFormat.wordSeparator != null) {
                result.append(convertedWord);
                if(!lastWord) {
                    result.append(destinationFormat.wordSeparator);
                }
            } else {
                result.append(convertedWord);
            }
        }

        return result.toString();
    }

    private static List<String> getWords(String str, CaseFormat caseFormat) {
        if (caseFormat.wordSeparator != null) {
            return new ArrayList<>(Arrays.asList(str.split(Pattern.quote(caseFormat.wordSeparator + ""))));
        }

        List<String> words = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char character = str.charAt(i);

            if (i == (str.length() - 1)) {
                //last word
                sb.append(character);
                words.add(sb.toString());
            }
            else if(caseFormat.wordBoundary.apply(str, i)) {
                //word found
                words.add(sb.toString());
                sb = new StringBuilder();
                sb.append(character);
            }
            else {
                sb.append(character);
            }
        }

        return words;
    }
}
