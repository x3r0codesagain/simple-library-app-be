package com.app.librarybackend.exception;

import com.app.librarybackend.model.enums.ErrorCodes;
import lombok.Data;

@Data
public class AppException extends RuntimeException{

  private final ErrorCodes code;

  public AppException(String message, ErrorCodes code) {
    super(message);
    this.code = code;
  }
}
