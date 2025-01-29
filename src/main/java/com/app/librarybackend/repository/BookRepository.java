package com.app.librarybackend.repository;

import com.app.librarybackend.model.Author;
import com.app.librarybackend.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
  Book findByTitleAndAuthorAndYear(String title, Author author, int year);

  Book findByIdAndBorrowed(String id, boolean borrowed);
  void deleteAllByAuthor(Author author);

  List<Book> findAllByAuthor(Author author);
}
