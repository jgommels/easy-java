package com.jgommels.easyjava.range;

import java.util.Objects;

/**
 * A mutable implementation of {@link Range}.
 */
public class MutableRange<T extends Comparable<? super T>>  implements Range<T> {
    private T start;
    private T end;

    private MutableRange(T start, T end) {
        RangeUtils.validate(start, end);
        this.start = start;
        this.end = end;
    }

    public static <T extends Comparable<? super T>> MutableRange<T> of(T start, T end) {
        return new MutableRange<>(start, end);
    }

    @Override
    public T getStart() {
        return start;
    }

    @Override
    public T getEnd() {
        return end;
    }

    public void setStart(T start) {
        RangeUtils.validate(start, this.end);
        this.start = start;
    }

    public void setEnd(T end) {
        RangeUtils.validate(this.start, end);
        this.end = end;
    }

    @Override
    public String toString() {
        return "MutableRange{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutableRange<?> that = (MutableRange<?>) o;
        return Objects.equals(start, that.start) &&
                Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}