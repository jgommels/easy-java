package com.jgommels.easyjava.range;

public class Range<T extends Comparable<? super T>> {
    private final T start;
    private final T end;

    private Range(T start, T end) {
        this.start = start;
        this.end = end;
    }

    public static <T extends Comparable<? super T>> Range<T> of(T start, T end) {
        return new Range<>(start, end);
    }

    public T getStart() {
        return start;
    }

    public T getEnd() {
        return end;
    }

    public boolean includes(T value) {
        return this.start.compareTo(value) <= 0 && this.end.compareTo(value) >= 0;
    }


    @Override
    public String toString() {
        return "Range{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}