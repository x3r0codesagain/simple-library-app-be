package com.app.librarybackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book extends BaseMongoEntity{
  private String title;

  @DocumentReference
  private Author author;
  private int year;
  private String category;
  private boolean borrowed;
}
