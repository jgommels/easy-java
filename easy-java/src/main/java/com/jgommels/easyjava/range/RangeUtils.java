package com.jgommels.easyjava.range;

import com.jgommels.easyjava.exception.ValidationException;

class RangeUtils {

    static <T extends Comparable<? super T>> void validate(T start, T end) {
        if(start == null) {
            throw new NullPointerException("Range start cannot be null.");
        }

        if(end == null) {
            throw new NullPointerException("Range end cannot be null.");
        }

        if(start.compareTo(end) > 0) {
            throw new ValidationException("Range start cannot be after range end.");
        }
    }
}