package com.jgommels.easyjava.validation;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * Utility class that provides functionality for validating Java beans using the javax.validation annotations.
 */
public class ValidationUtils {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Validates the bean against its javax.validation annotations.
     *
     * @throws ValidationException if the bean has failed validation
     */
    public static <T> void validate(T bean) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean);

        if (!violations.isEmpty()) {
            StringBuilder errorMsgBuilder = new StringBuilder();
            errorMsgBuilder.append("Validation error(s) occurred:\n");

            for(ConstraintViolation<T> violation : violations) {
                errorMsgBuilder.append(violation.getPropertyPath().toString()).append(": ").append(violation.getMessage()).append("\n");
            }

            throw new ValidationException(errorMsgBuilder.toString());
        }
    }
}