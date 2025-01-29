package com.app.librarybackend.model.request;

import com.app.librarybackend.model.BorrowedBook;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddMemberRequest {
  private String name;
  private String gender;
  private String email;
  private String phone;
  private List<BorrowedBook> borrowedBooks = new ArrayList<>();
}
