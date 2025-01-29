package com.app.librarybackend.service.impl;

import com.app.librarybackend.exception.AppException;
import com.app.librarybackend.model.Author;
import com.app.librarybackend.model.Book;
import com.app.librarybackend.model.BorrowedBook;
import com.app.librarybackend.model.Member;
import com.app.librarybackend.model.enums.ErrorCodes;
import com.app.librarybackend.model.request.AuthorRequest;
import com.app.librarybackend.repository.AuthorRepository;
import com.app.librarybackend.repository.BookRepository;
import com.app.librarybackend.repository.BorrowedBookRepository;
import com.app.librarybackend.repository.MemberRepository;
import com.app.librarybackend.response.model.AuthorResponse;
import com.app.librarybackend.response.model.BookResponse;
import com.app.librarybackend.response.model.MemberResponse;
import com.app.librarybackend.service.AuthorService;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired BookRepository bookRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private BorrowedBookRepository borrowedBookRepository;

  @Autowired
  private Mapper mapper;

  @Override
  public AuthorResponse addAuthor(AuthorRequest request) {
    if (StringUtils.isBlank(request.getName()) || StringUtils.isBlank(request.getGender())) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }

    Author savedAuthor = authorRepository.findById(request.getId()).orElseGet(() -> null);

    if (Objects.nonNull(savedAuthor) && !savedAuthor.getId().equals(request.getId())) {
      throw new AppException(ErrorCodes.AUTHOR_EXISTS.getMessage(), ErrorCodes.AUTHOR_EXISTS);
    }

    if ("".equals(request.getId())) {
      request.setId(null);
    }

    Author author = mapper.map(request, Author.class);
    Date now = new Date();
    if (StringUtils.isNotBlank(request.getId())) {
      author.setId(request.getId());
      author.setCreatedDate(savedAuthor.getCreatedDate());
      author.setUpdatedDate(now);
    } else {
      author.setCreatedDate(now);
      author.setUpdatedDate(now);
    }

    authorRepository.save(author);

    AuthorResponse response = mapper.map(author, AuthorResponse.class);

    return response;
  }

  @Override
  public List<AuthorResponse> getAuthors() {
    List<Author> authors = authorRepository.findAll();

    if (authors.isEmpty()) {
      return new ArrayList<>();
    }

    List<AuthorResponse> authorResponses =
        authors.stream().map(author -> mapper.map(author, AuthorResponse.class))
            .collect(Collectors.toList());
    return authorResponses;
  }

  @Override
  public AuthorResponse deleteAuthor(String id) {
    Author author = authorRepository.findById(id).orElseGet(() -> null);

    if (Objects.isNull(author)) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }

    bookRepository.findAllByAuthor(author).stream().filter(book -> book.isBorrowed()).forEach(book -> {
      BorrowedBook borrowedBook = borrowedBookRepository.findByBook(book);
      List<Member> members = memberRepository.findAll();

      Member member = members.stream()
          .filter(m -> m.getBorrowedBooks().contains(borrowedBook)).findFirst().orElseGet(() -> null);

      if (Objects.nonNull(member)) {
        member.getBorrowedBooks().remove(borrowedBook);
        member.setUpdatedDate(new Date());
        memberRepository.save(member);
        borrowedBookRepository.deleteByBook(book);
      }
    });


    bookRepository.deleteAllByAuthor(author);
    authorRepository.deleteById(id);

    return mapper.map(author, AuthorResponse.class);
  }

  @Override
  public AuthorResponse getAuthor(String id) {
    Author author = authorRepository.findById(id).orElseGet(() -> null);

    if (Objects.isNull(author)) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }

    return mapper.map(author, AuthorResponse.class);
  }
}
