package com.app.librarybackend.response.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookResponse extends BaseResponse {
  private BookResponse book;
  private String borrowDate;
}
