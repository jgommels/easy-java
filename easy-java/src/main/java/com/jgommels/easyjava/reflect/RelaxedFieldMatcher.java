package com.jgommels.easyjava.reflect;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maintains the mappings between field names and their associated {@link Field} instances, and provides functionality
 * for relaxed matching against those fields.
 *
 */
public class RelaxedFieldMatcher {
    /**
     * The list of fields maintained by this field matcher.
     */
    private final List<Field> fieldList;

    /**
     * A cache that maintains the relationships between strings and the Fields they can be mapped to.
     */
    private Map<String, Field> fieldMapping = new HashMap<>();


    private RelaxedFieldMatcher(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    /**
     * Returns the field that matches the given String. The matching is relaxed, so the type of case does not need to match
     * (e.g. my_field will map to myField).
     */
    public Field getMatchingField(String sourceName) {
        Field mapping = fieldMapping.get(sourceName);

        if(mapping == null) {
            mapping = lookupFieldAndCacheMapping(sourceName);
        }

        if(mapping != null) {
            return mapping;
        }

        //TODO Consider adding option to throw exception instead when no mapping is found
        return null;
    }

    private Field lookupFieldAndCacheMapping(String sourceName) {
        String normalizedSourceName = normalizeName(sourceName);

        for(Field field : this.fieldList) {
            if(normalizedSourceName.equals(normalizeName(field.getName()))) {
                this.fieldMapping.put(sourceName, field);
                return field;
            }
        }

        return null;
    }

    public static RelaxedFieldMatcher build(Field[] fields) {
        return new RelaxedFieldMatcher(Arrays.asList(fields));
    }


    private static String normalizeName(String str) {
        String alphaNumeric = str.replaceAll("[^a-zA-Z0-9]+", "");

        if(StringUtils.isEmpty(alphaNumeric)) {
            throw new IllegalArgumentException("Invalid name: " + str);
        }
        
        return alphaNumeric.toLowerCase();
    }
}
