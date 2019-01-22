package com.jgommels.easyjava.range;

/**
 * A range between two objects that implement {@link Comparable}.
 */
public interface Range<T extends Comparable<? super T>> extends Comparable<Range<T>> {
    T getStart();
    T getEnd() ;

    default boolean includes(T value) {
        return getStart().compareTo(value) <= 0 && getEnd().compareTo(value) >= 0;
    }

    @Override
    default int compareTo(Range<T> other) {
        return getStart().compareTo(other.getStart());
    }
}
