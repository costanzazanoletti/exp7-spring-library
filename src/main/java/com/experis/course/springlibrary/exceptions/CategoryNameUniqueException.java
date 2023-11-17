package com.experis.course.springlibrary.exceptions;

public class CategoryNameUniqueException extends RuntimeException {

  public CategoryNameUniqueException(String message) {
    super(message);
  }
}
