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
@Document(collection = "borrowed_books")
public class BorrowedBook extends BaseMongoEntity{

  @DocumentReference
  private Book book;
  private String borrowDate;
}
