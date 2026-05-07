package com.virtusa.faculty.book.exception;

public class InvalidBookStateException extends RuntimeException {
  public InvalidBookStateException(String message) {
    super(message);
  }
}