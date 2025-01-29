package com.app.librarybackend.controller;

import com.app.librarybackend.exception.AppException;
import com.app.librarybackend.model.request.AuthorRequest;
import com.app.librarybackend.response.RestListResponse;
import com.app.librarybackend.response.RestSingleResponse;
import com.app.librarybackend.response.model.AuthorResponse;
import com.app.librarybackend.response.model.BookResponse;
import com.app.librarybackend.service.AuthorService;
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
@RequestMapping("api/v1/author")
public class AuthorController {

  @Autowired
  private AuthorService authorService;

  @PostMapping("/add")
  public RestSingleResponse<AuthorResponse> addAuthor(@RequestBody AuthorRequest request) {
    try {
      AuthorResponse response = authorService.addAuthor(request);

      return new RestSingleResponse<>(null, null, true, response);
    } catch (AppException appException) {
      return new RestSingleResponse<>(appException.getMessage(), appException.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestSingleResponse<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }

  @GetMapping("/get")
  public RestListResponse<AuthorResponse> getAuthors() {
    try {
      List<AuthorResponse> response = authorService.getAuthors();
      return new RestListResponse<>(null, null, true, response);
    } catch (AppException appException) {
      return new RestListResponse<>(appException.getMessage(), appException.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestListResponse<>("Internal Server Error",
          HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }

  @GetMapping("/getOne")
  public RestSingleResponse<AuthorResponse> getAuthor(@RequestParam String id) {
    try {
      AuthorResponse response = authorService.getAuthor(id);
      return new RestSingleResponse<>(null, null, true, response);
    } catch (AppException appException) {
      return new RestSingleResponse<>(appException.getMessage(), appException.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestSingleResponse<>("Internal Server Error",
          HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }

  @PostMapping("/delete")
  public RestSingleResponse<AuthorResponse> deleteAuthor(@RequestParam String id) {
    try {
      AuthorResponse response = authorService.deleteAuthor(id);

      return new RestSingleResponse<>(null, null, true, response);
    } catch (AppException appException) {
      return new RestSingleResponse<>(appException.getMessage(), appException.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestSingleResponse<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }
}
