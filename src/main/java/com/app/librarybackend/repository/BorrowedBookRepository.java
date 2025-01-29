package com.app.librarybackend.repository;

import com.app.librarybackend.model.Book;
import com.app.librarybackend.model.BorrowedBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowedBookRepository extends MongoRepository<BorrowedBook, String> {
  void deleteByBook(Book book);
  List<BorrowedBook> findAllById(List<String> ids);

  BorrowedBook findByBook(Book book);
}
