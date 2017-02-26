package com.jgommels.easyjava.strings

import spock.lang.Specification

/**
 *
 */
class CaseFormatsTest extends Specification {
    def "getCaseFormat"(String source, CaseFormat caseFormat) {
        expect:
        CaseFormats.getCaseFormat(source) == caseFormat

        where:
        source                     | caseFormat
        "lowerCamel"               | CaseFormat.LOWER_CAMEL
        "lowerCamel2"              | CaseFormat.LOWER_CAMEL
        "anotherLowerCamel"        | CaseFormat.LOWER_CAMEL
        "lower-hyphen"             | CaseFormat.LOWER_HYPHEN
        "lower-hyphen2"            | CaseFormat.LOWER_HYPHEN
        "another-lower-hyphen"     | CaseFormat.LOWER_HYPHEN
        "lower.period"             | CaseFormat.LOWER_PERIOD
        "lower.period2"            | CaseFormat.LOWER_PERIOD
        "another.lower.period"     | CaseFormat.LOWER_PERIOD
        "lower_underscore"         | CaseFormat.LOWER_UNDERSCORE
        "lower_underscore2"        | CaseFormat.LOWER_UNDERSCORE
        "another_lower_underscore" | CaseFormat.LOWER_UNDERSCORE

        "UpperCamel"               | CaseFormat.UPPER_CAMEL
        "UpperCamel2"              | CaseFormat.UPPER_CAMEL
        "AnotherUpperCamel"        | CaseFormat.UPPER_CAMEL
        "UPPER-HYPHEN"             | CaseFormat.UPPER_HYPHEN
        "UPPER-HYPHEN2"            | CaseFormat.UPPER_HYPHEN
        "ANOTHER-UPPER-HYPHEN"     | CaseFormat.UPPER_HYPHEN
        "UPPER.PERIOD"             | CaseFormat.UPPER_PERIOD
        "UPPER.PERIOD2"            | CaseFormat.UPPER_PERIOD
        "ANOTHER.UPPER.PERIOD"     | CaseFormat.UPPER_PERIOD
        "UPPER_UNDERSCORE"         | CaseFormat.UPPER_UNDERSCORE
        "UPPER_UNDERSCORE2"        | CaseFormat.UPPER_UNDERSCORE
        "ANOTHER_UPPER_UNDERSCORE" | CaseFormat.UPPER_UNDERSCORE

        "miXed-HYphen"             | CaseFormat.MIXED_HYPHEN
        "ANother-miXed1-HYphen2"   | CaseFormat.MIXED_HYPHEN
        "MIxed.perIOD"             | CaseFormat.MIXED_PERIOD
        "anoTHER.M1xed.perI0D"     | CaseFormat.MIXED_PERIOD
        "mixeD_unDERscorE"         | CaseFormat.MIXED_UNDERSCORE
        "ANOTHEr_mix3D_unD3RscorE" | CaseFormat.MIXED_UNDERSCORE
    }

    def "convertCaseFormat"(String source, CaseFormat caseFormat, String expected) {
        expect:
        CaseFormats.convertCase(source, caseFormat) == expected

        where:
        source           | caseFormat                  | expected
        "myTestString"   | CaseFormat.LOWER_CAMEL      | "myTestString"
        "myTestString"   | CaseFormat.LOWER_HYPHEN     | "my-test-string"
        "myTestString"   | CaseFormat.LOWER_PERIOD     | "my.test.string"
        "myTestString"   | CaseFormat.LOWER_UNDERSCORE | "my_test_string"

        "myTestString"   | CaseFormat.UPPER_CAMEL      | "MyTestString"
        "myTestString"   | CaseFormat.UPPER_HYPHEN     | "MY-TEST-STRING"
        "myTestString"   | CaseFormat.UPPER_PERIOD     | "MY.TEST.STRING"
        "myTestString"   | CaseFormat.UPPER_UNDERSCORE | "MY_TEST_STRING"

        "my-test-string" | CaseFormat.LOWER_CAMEL      | "myTestString"
        "my-test-string" | CaseFormat.LOWER_HYPHEN     | "my-test-string"
        "my-test-string" | CaseFormat.LOWER_PERIOD     | "my.test.string"
        "my-test-string" | CaseFormat.LOWER_UNDERSCORE | "my_test_string"

        "my-test-string" | CaseFormat.UPPER_CAMEL      | "MyTestString"
        "my-test-string" | CaseFormat.UPPER_HYPHEN     | "MY-TEST-STRING"
        "my-test-string" | CaseFormat.UPPER_PERIOD     | "MY.TEST.STRING"
        "my-test-string" | CaseFormat.UPPER_UNDERSCORE | "MY_TEST_STRING"

        "my.test.string" | CaseFormat.LOWER_CAMEL      | "myTestString"
        "my.test.string" | CaseFormat.LOWER_HYPHEN     | "my-test-string"
        "my.test.string" | CaseFormat.LOWER_PERIOD     | "my.test.string"
        "my.test.string" | CaseFormat.LOWER_UNDERSCORE | "my_test_string"

        "my.test.string" | CaseFormat.UPPER_CAMEL      | "MyTestString"
        "my.test.string" | CaseFormat.UPPER_HYPHEN     | "MY-TEST-STRING"
        "my.test.string" | CaseFormat.UPPER_PERIOD     | "MY.TEST.STRING"
        "my.test.string" | CaseFormat.UPPER_UNDERSCORE | "MY_TEST_STRING"

        "my_test_string" | CaseFormat.LOWER_CAMEL      | "myTestString"
        "my_test_string" | CaseFormat.LOWER_HYPHEN     | "my-test-string"
        "my_test_string" | CaseFormat.LOWER_PERIOD     | "my.test.string"
        "my_test_string" | CaseFormat.LOWER_UNDERSCORE | "my_test_string"

        "my_test_string" | CaseFormat.UPPER_CAMEL      | "MyTestString"
        "my_test_string" | CaseFormat.UPPER_HYPHEN     | "MY-TEST-STRING"
        "my_test_string" | CaseFormat.UPPER_PERIOD     | "MY.TEST.STRING"
        "my_test_string" | CaseFormat.UPPER_UNDERSCORE | "MY_TEST_STRING"

        //Mixed Cases
        "my-teST-sTRINg" | CaseFormat.LOWER_CAMEL      | "myTestString"
        "my-teST-sTRINg" | CaseFormat.LOWER_HYPHEN     | "my-test-string"
        "my-teST-sTRINg" | CaseFormat.LOWER_PERIOD     | "my.test.string"

        "my-teST-sTRINg" | CaseFormat.UPPER_CAMEL      | "MyTestString"
        "my-teST-sTRINg" | CaseFormat.UPPER_HYPHEN     | "MY-TEST-STRING"
        "my-teST-sTRINg" | CaseFormat.UPPER_PERIOD     | "MY.TEST.STRING"

        "my.teST.sTRINg" | CaseFormat.LOWER_CAMEL      | "myTestString"
        "my.teST.sTRINg" | CaseFormat.LOWER_HYPHEN     | "my-test-string"
        "my.teST.sTRINg" | CaseFormat.LOWER_PERIOD     | "my.test.string"

        "my.teST.sTRINg" | CaseFormat.UPPER_CAMEL      | "MyTestString"
        "my.teST.sTRINg" | CaseFormat.UPPER_HYPHEN     | "MY-TEST-STRING"
        "my.teST.sTRINg" | CaseFormat.UPPER_PERIOD     | "MY.TEST.STRING"

        "my_teST_sTRINg" | CaseFormat.LOWER_CAMEL      | "myTestString"
        "my_teST_sTRINg" | CaseFormat.LOWER_HYPHEN     | "my-test-string"
        "my_teST_sTRINg" | CaseFormat.LOWER_PERIOD     | "my.test.string"

        "my_teST_sTRINg" | CaseFormat.UPPER_CAMEL      | "MyTestString"
        "my_teST_sTRINg" | CaseFormat.UPPER_HYPHEN     | "MY-TEST-STRING"
        "my_teST_sTRINg" | CaseFormat.UPPER_PERIOD     | "MY.TEST.STRING"
    }
}