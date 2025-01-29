package com.app.librarybackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Document(collection = "members")
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseMongoEntity{

  private String name;
  private String gender;
  private String email;
  private String phone;

  @DocumentReference
  private List<BorrowedBook> borrowedBooks;
}
