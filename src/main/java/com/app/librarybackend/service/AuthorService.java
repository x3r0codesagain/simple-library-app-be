package com.app.librarybackend.service;

import com.app.librarybackend.model.request.AuthorRequest;
import com.app.librarybackend.response.model.AuthorResponse;

import java.util.List;

public interface AuthorService {
  AuthorResponse addAuthor(AuthorRequest request);
  List<AuthorResponse> getAuthors();
  AuthorResponse deleteAuthor(String id);
  AuthorResponse getAuthor(String id);
}
