package com.jgommels.easyjava.range

import com.jgommels.easyjava.exception.ValidationException
import spock.lang.Specification

import java.time.LocalDateTime

class RangeTest extends Specification {

    def 'test inclusion and exclusion of dates within LocalDateTime range'() {
        when:
        def start = LocalDateTime.parse("2018-01-01T00:00")
        def end = LocalDateTime.parse("2019-01-01T00:00")
        Range<LocalDateTime> range = ImmutableRange.of(start, end)

        then:
        range.getStart() == start
        range.getEnd() == end
        !range.includes(LocalDateTime.parse("2017-12-31T00:00"))
        range.includes(LocalDateTime.parse("2018-01-01T00:00"))
        range.includes(LocalDateTime.parse("2018-06-01T00:00"))
        range.includes(LocalDateTime.parse("2019-01-01T00:00"))
        !range.includes(LocalDateTime.parse("2019-01-02T00:00"))
    }

    def 'expect validation exception when start is after end'() {
        when:
        def start = LocalDateTime.parse("2019-01-01T00:00")
        def end = LocalDateTime.parse("2018-01-01T00:00")
        ImmutableRange.of(start, end)

        then:
        thrown ValidationException
    }

    def 'expect validation exception when setting start after end'() {
        when:
        def start = LocalDateTime.parse("2018-01-01T00:00")
        def end = LocalDateTime.parse("2019-01-01T00:00")
        MutableRange<LocalDateTime> range = MutableRange.of(start, end)
        range.setStart(LocalDateTime.parse("2020-01-01T00:00"))

        then:
        thrown ValidationException
    }

    def 'expect validation exception when setting end before start'() {
        when:
        def start = LocalDateTime.parse("2018-01-01T00:00")
        def end = LocalDateTime.parse("2019-01-01T00:00")
        MutableRange<LocalDateTime> range = MutableRange.of(start, end)
        range.setEnd(LocalDateTime.parse("2017-01-01T00:00"))

        then:
        thrown ValidationException
    }

    def 'expect MutableRange to update properly'() {
        given:
        def start = LocalDateTime.parse("2018-01-01T00:00")
        def end = LocalDateTime.parse("2019-01-01T00:00")
        MutableRange<LocalDateTime> range = MutableRange.of(start, end)

        when:
        range.setStart(LocalDateTime.parse("2016-01-01T00:00"))
        range.setEnd(LocalDateTime.parse("2017-01-01T00:00"))

        then:
        range.getStart() == LocalDateTime.parse("2016-01-01T00:00")
        range.getEnd() == LocalDateTime.parse("2017-01-01T00:00")
        range.includes(LocalDateTime.parse("2016-06-01T00:00"))
        !range.includes(LocalDateTime.parse("2018-06-01T00:00"))
    }
}
