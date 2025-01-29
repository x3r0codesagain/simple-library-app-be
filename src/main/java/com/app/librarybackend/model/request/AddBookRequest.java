package com.app.librarybackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddBookRequest {
  private String id;
  private String title;
  private String authorName;
  private int year;
  private String category;
  private boolean borrowed;
}
