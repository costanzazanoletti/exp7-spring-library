package com.experis.course.springlibrary.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = YearPastOrPresentValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface YearPastOrPresent {

  String message() default "Year must be present or past";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
