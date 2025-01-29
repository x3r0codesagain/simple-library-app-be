package com.app.librarybackend.response.model;

import com.app.librarybackend.model.BorrowedBook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse extends BaseResponse{
  private String name;
  private String gender;
  private String email;
  private String phone;

  private List<BorrowedBookResponse> borrowedBooks;
}
