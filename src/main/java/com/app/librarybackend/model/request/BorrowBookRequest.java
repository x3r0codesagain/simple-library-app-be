package com.app.librarybackend.model.request;

import com.app.librarybackend.response.model.BaseResponse;
import com.app.librarybackend.response.model.BookResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowBookRequest {
  private String bookId;
  private String email;
}
