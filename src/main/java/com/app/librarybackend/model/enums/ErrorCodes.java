package com.app.librarybackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {

  BOOK_EXISTED("Book is already in database"),
  BAD_REQUEST("Incomplete or broken request"),
  AUTHOR_EXISTS("Author is already in database"),
  MEMBER_EXISTS("Member is registered");

  private String message;
}
