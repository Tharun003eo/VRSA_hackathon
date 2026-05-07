package com.virtusa.faculty.course.exception;

public class MaterialNotFoundException extends RuntimeException {
  public MaterialNotFoundException(Long id) {
    super("Course material not found with id: " + id);
  }
}