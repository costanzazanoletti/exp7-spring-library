package com.experis.course.springlibrary.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class YearPastOrPresentValidator implements ConstraintValidator<YearPastOrPresent, Integer> {

  @Override
  public void initialize(YearPastOrPresent constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(Integer fieldValue,
      ConstraintValidatorContext constraintValidatorContext) {
    // logica custom di validazione sul valore del campo che è stato annotato
    return fieldValue != null && fieldValue <= LocalDate.now().getYear();
  }
}
