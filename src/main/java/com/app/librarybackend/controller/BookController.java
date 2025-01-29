package com.app.librarybackend.controller;

import com.app.librarybackend.exception.AppException;
import com.app.librarybackend.model.request.AddBookRequest;
import com.app.librarybackend.model.request.BorrowBookRequest;
import com.app.librarybackend.response.RestListResponse;
import com.app.librarybackend.response.RestSingleResponse;
import com.app.librarybackend.response.model.AuthorResponse;
import com.app.librarybackend.response.model.BookResponse;
import com.app.librarybackend.response.model.BorrowedBookResponse;
import com.app.librarybackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/book")
public class BookController {

  @Autowired
  private BookService bookService;

  @PostMapping("/add")
  public RestSingleResponse<BookResponse> addBook(@RequestBody AddBookRequest request) {
    try {
      BookResponse response = bookService.addBook(request);
      return new RestSingleResponse<>(null, null, true, response);
    } catch (AppException appException) {
      return new RestSingleResponse<>(appException.getMessage(), appException.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestSingleResponse<>("Internal Server Error",
          HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }

  @GetMapping("/get")
  public RestListResponse<BookResponse> getBooks() {
    try {
      List<BookResponse> response = bookService.getBooks();
      return new RestListResponse<>(null, null, true, response);
    } catch (AppException appException) {
      return new RestListResponse<>(appException.getMessage(), appException.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestListResponse<>("Internal Server Error",
          HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }

  @GetMapping("/getOne")
  public RestSingleResponse<BookResponse> getBook(@RequestParam String id) {
    try {
      BookResponse response = bookService.getBook(id);
      return new RestSingleResponse<>(null, null, true, response);
    } catch (AppException appException) {
      return new RestSingleResponse<>(appException.getMessage(), appException.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestSingleResponse<>("Internal Server Error",
          HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }

  @PostMapping("/delete")
  public RestSingleResponse<BookResponse> deleteBook(@RequestParam String bookId) {
    try {
      BookResponse response = bookService.deleteBook(bookId);
      return new RestSingleResponse<>(null, null, true, response);
    } catch (AppException appException) {
      return new RestSingleResponse<>(appException.getMessage(), appException.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestSingleResponse<>("Internal Server Error",
          HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }

  @PostMapping("/borrow")
  public RestSingleResponse<BorrowedBookResponse> borrowBook(@RequestBody BorrowBookRequest request) {
    try {
      BorrowedBookResponse response = bookService.borrowBook(request);
      return new RestSingleResponse<>(null, null, true, response);
    } catch (AppException appException) {
      return new RestSingleResponse<>(appException.getMessage(), appException.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestSingleResponse<>("Internal Server Error",
          HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }

  @PostMapping("/return")
  public RestSingleResponse<BorrowedBookResponse> returnBook(@RequestParam String id) {
    try {
      BorrowedBookResponse response = bookService.returnBook(id);
      return new RestSingleResponse<>(null, null, true, response);
    } catch (AppException appException) {
      return new RestSingleResponse<>(appException.getMessage(), appException.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestSingleResponse<>("Internal Server Error",
          HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }

  @GetMapping("/getBorrowed")
  public RestListResponse<BorrowedBookResponse> getBorrowedBooks() {
    try {
      List<BorrowedBookResponse> response = bookService.getBorrowedBooks();
      return new RestListResponse<>(null, null, true, response);
    } catch (AppException appException) {
      return new RestListResponse<>(appException.getMessage(), appException.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestListResponse<>("Internal Server Error",
          HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }


}
