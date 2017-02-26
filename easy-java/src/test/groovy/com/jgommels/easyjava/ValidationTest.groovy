package com.jgommels.easyjava

import com.jgommels.easyjava.validation.ValidationUtils
import spock.lang.Specification

import javax.validation.ValidationException
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 *
 */
class ValidationTest extends Specification {

    def "testMinAnnotation"() {
        given:
            ValidationBean bean = new ValidationBean();
            bean.str = "22";
            bean.primInt = 3;

        when:
            ValidationUtils.validate(bean);

        then:
            thrown ValidationException
    }

    private static class ValidationBean {
        @NotNull private String str;
        @Min(5L) private int primInt;
    }

}
