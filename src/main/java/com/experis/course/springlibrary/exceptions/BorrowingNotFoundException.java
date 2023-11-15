package com.experis.course.springlibrary.exceptions;

public class BorrowingNotFoundException extends RuntimeException {

  public BorrowingNotFoundException(String message) {
    super(message);
  }
}
