package com.app.librarybackend.repository;

import com.app.librarybackend.model.BorrowedBook;
import com.app.librarybackend.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {
  Member findByEmail(String email);
}
