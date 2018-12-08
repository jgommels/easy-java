package com.jgommels.easyjava.strings;

import java.util.function.BiFunction;
import java.util.regex.Pattern;

public enum CaseFormat {
    //Note: Ordering of these enums matters for matching Strings. Matching via regex will occur in order as shown here.

    /**
     * Camel case where the first letter is a lower-case letter. e.g. lowerCamel
     */
    LOWER_CAMEL(Pattern.compile("^([a-z][a-zA-Z0-9]+)+$"), null,
            (str, index) -> index == str.length() - 1 || Character.isUpperCase(str.charAt(index)),
            (word, isFirstWord) -> {
                char firstLetter = isFirstWord ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0));
                return word.length() == 1 ? firstLetter + "" : firstLetter + word.substring(1).toLowerCase();
            }),
    /**
     * Camel case where the first letter is an upper-case letter. e.g. upperCamel
     */
    UPPER_CAMEL(Pattern.compile("^([A-Z][a-zA-Z0-9]+)+$"), null,
            (str, index) -> index == str.length() - 1 || (index != 0 && Character.isUpperCase(str.charAt(index))),
            (word, isFirstWord) -> {
                char firstLetter = Character.toUpperCase(word.charAt(0));
                return word.length() == 1 ? firstLetter + "" : firstLetter + word.substring(1).toLowerCase();
            }),

    /**
     * Case with grouping of lower-case letters and numbers separated by periods.
     * First character must be a lower-case letter. e.g. lower.period
     */
    LOWER_PERIOD(Pattern.compile("^([a-z][a-z0-9.]+)+$"), '.', null, CaseFormat::toLowerCase),

    /**
     * Case with grouping of lower-case letters and numbers separated by hyphens.
     * First character must be a lower-case letter. eg. lower-hyphen
     */
    LOWER_HYPHEN(Pattern.compile("^([a-z][a-z0-9\\-]+)+$"), '-', null, CaseFormat::toLowerCase),

    /**
     * Case with grouping of lower-case letters and numbers separated by underscores.
     * First character must be a lower-case letter. e.g. lower_underscore
     */
    LOWER_UNDERSCORE(Pattern.compile("^([a-z][a-z0-9_]+)+$"), '_', null, CaseFormat::toLowerCase),

    /**
     * Case with grouping of upper-case letters and numbers separated by periods.
     * First character must be a upper-case letter. e.g. UPPER.PERIOD
     */
    UPPER_PERIOD(Pattern.compile("^([A-Z][A-Z0-9.]+)+$"), '.', null, CaseFormat::toUpperCase),

    /**
     * Case with grouping of upper-case letters and numbers separated by hyphens.
     * First character must be a upper-case letter. eg. UPPER-HYPHEN
     */
    UPPER_HYPHEN(Pattern.compile("^([A-Z][A-Z0-9\\-]+)+$"), '-', null, CaseFormat::toUpperCase),

    /**
     * Case with grouping of upper-case letters and numbers separated by underscores.
     * First character must be a upper-case letter. e.g. UPPER_UNDERSCORE
     */
    UPPER_UNDERSCORE(Pattern.compile("^([A-Z][A-Z0-9_]+)+$"), '_', null, CaseFormat::toUpperCase),

    /**
     * Case with grouping of a mixture of lower and upper case letters and numbers separated by periods.
     * Strings cannot be converted to this case type. e.g. miXEd.PeRiOD
     */
    MIXED_PERIOD(Pattern.compile("^([a-zA-Z][a-zA-Z0-9.]+)+$"), '.', null, null),

    /**
     * Case with grouping of a mixture of lower and upper case letters and numbers separated by hyphens.
     * Strings cannot be converted to this case type. e.g. MIxeD.hYphen
     */
    MIXED_HYPHEN(Pattern.compile("^([a-zA-Z][a-zA-Z0-9\\-]+)+$"), '-', null, null),

    /**
     * Case with grouping of a mixture of lower and upper case letters and numbers separated by underscores.
     * Strings cannot be converted to this case type. e.g. MIXEd.unDersCORE
     */
    MIXED_UNDERSCORE(Pattern.compile("^([a-zA-Z][a-zA-Z0-9_]+)+$"), '_', null, null);


    /** Regular expression used to determine whether a string matches this CaseFormat.*/
    final Pattern regex;

    /** A character that separates words for this CaseFormat. May be null if not applicable. */
    final Character wordSeparator;

    /** A function that indicates whether or not a character in the string represents the end of a word. May be null if not applicable. */
    final BiFunction<String, Integer, Boolean> wordBoundary;

    /** A function with the parameters (word, isFirstWord) that converts a lower-case word to a word in this CaseFormat*/
    final BiFunction<String, Boolean, String> wordConverter;

    CaseFormat(Pattern regex, Character wordSeparator, BiFunction<String, Integer, Boolean> wordBoundary,
               BiFunction<String, Boolean, String> wordConverter) {
        if (wordSeparator == null && wordBoundary == null) {
            throw new NullPointerException("wordSeparator and wordBoundary can't both be null");
        }

        if(wordSeparator != null && wordBoundary != null) {
            throw new IllegalStateException("either wordSeparator or wordBoundary must be null");
        }

        this.wordSeparator = wordSeparator;
        this.wordBoundary = wordBoundary;
        this.regex = regex;
        this.wordConverter = wordConverter;
    }

    /**
     *
     * @return whether a String can be converted to this case format.
     */
    public boolean canBeConvertedTo() {
        return wordConverter != null;
    }

    private static String toLowerCase(String word, @SuppressWarnings("unused") Boolean isFirstWord) {
        return word.toLowerCase();
    }

    private static String toUpperCase(String word, @SuppressWarnings("unused") Boolean isFirstWord) {
        return word.toUpperCase();
    }

    static CaseFormat getCaseFormat(String str) {
        for(CaseFormat caseFormat : CaseFormat.values()) {
            if(caseFormat.regex.matcher(str).matches()) {
                return caseFormat;
            }
        }

        throw new IllegalArgumentException("No supported CaseFormat for: " + str);
    }
}
