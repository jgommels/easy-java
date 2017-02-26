package com.jgommels.easyjava.bean;

import com.jgommels.easyjava.converter.InstantConverter;
import com.jgommels.easyjava.converter.LocalDateConverter;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;

import java.time.Instant;
import java.time.LocalDate;

public class BeanUtilsBeanFactory {

    /**
     * Constructs and returns a {@link BeanUtilsBean} with default registered converters.
     */
    public static BeanUtilsBean getBeanUtilsBean() {
        ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
        convertUtilsBean.register(new InstantConverter(), Instant.class);
        convertUtilsBean.register(new LocalDateConverter(), LocalDate.class) ;

        return new BeanUtilsBean(convertUtilsBean);
    }
}
