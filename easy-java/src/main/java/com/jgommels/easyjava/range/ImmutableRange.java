package com.jgommels.easyjava.range;

import java.util.Objects;

/**
 * An immutable implementation of {@link Range}.
 */
public final class ImmutableRange<T extends Comparable<? super T>>  implements Range<T> {
    private final T start;
    private final T end;

    private ImmutableRange(T start, T end) {
        RangeUtils.validate(start, end);
        this.start = start;
        this.end = end;
    }

    public static <T extends Comparable<? super T>> ImmutableRange<T> of(T start, T end) {
        return new ImmutableRange<>(start, end);
    }

    @Override
    public T getStart() {
        return start;
    }

    @Override
    public T getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "ImmutableRange{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmutableRange<?> range = (ImmutableRange<?>) o;
        return Objects.equals(start, range.start) &&
                Objects.equals(end, range.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}