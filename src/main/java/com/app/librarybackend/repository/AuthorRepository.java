package com.app.librarybackend.repository;

import com.app.librarybackend.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
  Author findByName(String name);
}
