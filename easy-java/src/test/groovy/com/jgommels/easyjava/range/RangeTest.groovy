package com.jgommels.easyjava.range

import spock.lang.Specification

import java.time.LocalDateTime

class RangeTest extends Specification {

    def 'test Range of LocalDateTime objects'() {
        given:
        def start = LocalDateTime.parse("2018-01-01T00:00")
        def end = LocalDateTime.parse("2019-01-01T00:00")
        Range<LocalDateTime> dateTimeRange = Range.of(start, end)

        expect:
        dateTimeRange.getStart() == start
        dateTimeRange.getEnd() == end
        !dateTimeRange.includes(LocalDateTime.parse("2017-12-31T00:00"))
        dateTimeRange.includes(LocalDateTime.parse("2018-01-01T00:00"))
        dateTimeRange.includes(LocalDateTime.parse("2018-06-01T00:00"))
        dateTimeRange.includes(LocalDateTime.parse("2019-01-01T00:00"))
        !dateTimeRange.includes(LocalDateTime.parse("2019-01-02T00:00"))
    }
}
