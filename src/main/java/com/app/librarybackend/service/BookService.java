package com.app.librarybackend.service;


import com.app.librarybackend.model.request.AddBookRequest;
import com.app.librarybackend.model.request.BorrowBookRequest;
import com.app.librarybackend.response.model.BookResponse;
import com.app.librarybackend.response.model.BorrowedBookResponse;

import java.text.ParseException;
import java.util.List;

public interface BookService {
  BookResponse addBook(AddBookRequest bookRequest);
  List<BookResponse> getBooks();
  BookResponse getBook(String id);
  BookResponse deleteBook(String bookId);
  BorrowedBookResponse borrowBook(BorrowBookRequest request) throws ParseException;
  BorrowedBookResponse returnBook(String id);
  List<BorrowedBookResponse> getBorrowedBooks();
}
