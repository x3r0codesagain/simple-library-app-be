package com.app.librarybackend.response.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse extends BaseResponse{
  private String title;
  private AuthorResponse author;
  private int year;
  private String category;
  private boolean borrowed;
}
