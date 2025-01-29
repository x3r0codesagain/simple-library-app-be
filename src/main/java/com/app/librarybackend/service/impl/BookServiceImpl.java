package com.app.librarybackend.service.impl;


import com.app.librarybackend.exception.AppException;
import com.app.librarybackend.model.Author;
import com.app.librarybackend.model.Book;
import com.app.librarybackend.model.BorrowedBook;
import com.app.librarybackend.model.Member;
import com.app.librarybackend.model.enums.ErrorCodes;
import com.app.librarybackend.model.request.AddBookRequest;
import com.app.librarybackend.model.request.BorrowBookRequest;
import com.app.librarybackend.repository.AuthorRepository;
import com.app.librarybackend.repository.BookRepository;
import com.app.librarybackend.repository.BorrowedBookRepository;
import com.app.librarybackend.repository.MemberRepository;
import com.app.librarybackend.response.model.BookResponse;
import com.app.librarybackend.response.model.BorrowedBookResponse;
import com.app.librarybackend.response.model.MemberResponse;
import com.app.librarybackend.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private BorrowedBookRepository borrowedBookRepository;

  @Autowired
  private Mapper mapper;

  @Override
  public BookResponse addBook(AddBookRequest bookRequest) {

    Author author = authorRepository.findByName(bookRequest.getAuthorName());
    if (Objects.isNull(author)) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }
    Book savedBook = bookRepository.findByTitleAndAuthorAndYear(bookRequest.getTitle(),
        author, bookRequest.getYear());
    if (Objects.nonNull(savedBook) && !savedBook.getId().equals(bookRequest.getId())) {
      throw new AppException(ErrorCodes.BOOK_EXISTED.getMessage(), ErrorCodes.BOOK_EXISTED);
    }

    if (StringUtils.isBlank(bookRequest.getAuthorName()) ||
      StringUtils.isBlank(bookRequest.getTitle()) ||
      StringUtils.isBlank(bookRequest.getCategory()) ||
      bookRequest.getYear() < 1 || Objects.isNull(author)) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }

    Book newBook = mapper.map(bookRequest, Book.class);

    if (StringUtils.isNotBlank(bookRequest.getId())) {
      newBook.setId(bookRequest.getId());
      newBook.setCreatedDate(Optional.ofNullable(savedBook).map(saved -> saved.getCreatedDate()).orElseGet(() -> new Date()));
      newBook.setUpdatedDate(new Date());
    } else {
      setCreatedDate(newBook);
    }

    newBook.setAuthor(author);

    bookRepository.save(newBook);

    BookResponse response = mapper.map(newBook, BookResponse.class);

    return response;
  }

  @Override
  public List<BookResponse> getBooks() {
    List<Book> books = bookRepository.findAll();

    if (books.isEmpty()) {
      return new ArrayList<>();
    }

    List<BookResponse> bookResponses =
        books.stream().map(book -> mapper.map(book, BookResponse.class))
            .collect(Collectors.toList());
    return bookResponses;
  }

  @Override
  public BookResponse getBook(String id) {
    Book book = bookRepository.findById(id).orElseGet(() -> null);

    if (Objects.isNull(book)) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }

    return mapper.map(book, BookResponse.class);
  }

  @Override
  public BookResponse deleteBook(String bookId) {
    Book book = bookRepository.findById(bookId).orElseGet(() -> null);

    if (Objects.isNull(book)) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }

    BorrowedBook borrowedBook = borrowedBookRepository.findByBook(book);
    List<Member> members = memberRepository.findAll();

    Member member = members.stream()
        .filter(m -> m.getBorrowedBooks().contains(borrowedBook)).findFirst().orElseGet(() -> null);

    if (Objects.nonNull(member)) {
      member.getBorrowedBooks().remove(borrowedBook);
      member.setUpdatedDate(new Date());
      memberRepository.save(member);
    }


    borrowedBookRepository.deleteByBook(book);
    bookRepository.deleteById(bookId);

    return mapper.map(book, BookResponse.class);
  }

  @Override
  public BorrowedBookResponse borrowBook(BorrowBookRequest request) throws ParseException {

    Book book = bookRepository.findById(request.getBookId()).orElseGet(() -> null);
    BorrowedBook borrowedBookDB = borrowedBookRepository.findByBook(book);
    Member member = memberRepository.findByEmail(request.getEmail());

    if (Objects.isNull(book) || Objects.isNull(member)) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }

    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String borrowDate = format.format(now);
    book.setBorrowed(true);

    BorrowedBook borrowedBook = BorrowedBook.builder()
        .book(book)
        .borrowDate(borrowDate)
        .build();

    if (Objects.nonNull(borrowedBookDB)) {
      Member old = memberRepository.
          findAll().stream().filter(brwBook -> brwBook.getBorrowedBooks().contains(borrowedBookDB))
          .findFirst().orElseGet(() -> null);

      if (Objects.nonNull(old)) {
        old.getBorrowedBooks().remove(borrowedBookDB);
        old.setUpdatedDate(now);
        memberRepository.save(old);
        borrowedBookRepository.deleteById(borrowedBookDB.getId());
      }
    }
    borrowedBook.setCreatedDate(now);
    borrowedBook.setUpdatedDate(now);

    book.setUpdatedDate(now);
    bookRepository.save(book);

    borrowedBookRepository.save(borrowedBook);

    member.getBorrowedBooks().add(borrowedBook);
    member.setUpdatedDate(now);
    memberRepository.save(member);

    BorrowedBookResponse response = mapper.map(borrowedBook, BorrowedBookResponse.class);

    return response;
  }

  @Override
  public BorrowedBookResponse returnBook(String id) {
    BorrowedBook borrowedBook = borrowedBookRepository.findById(id).orElseGet(() -> null);

    if (Objects.isNull(borrowedBook)) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }

    Book book = bookRepository.findByIdAndBorrowed(borrowedBook.getBook().getId(), true);

    if (Objects.isNull(book)) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }

    book.setBorrowed(false);
    book.setUpdatedDate(new Date());

    bookRepository.save(book);

    borrowedBookRepository.deleteById(id);
    borrowedBook.getBook().setBorrowed(false);

    return mapper.map(borrowedBook, BorrowedBookResponse.class);
  }

  @Override
  public List<BorrowedBookResponse> getBorrowedBooks() {
    List<BorrowedBook> books = borrowedBookRepository.findAll();

    if (books.isEmpty()) {
      return new ArrayList<>();
    }

    List<BorrowedBookResponse> bookResponses =
        books.stream().map(book -> mapper.map(book, BorrowedBookResponse.class))
            .collect(Collectors.toList());
    return bookResponses;
  }

  private void setCreatedDate(Book newBook) {
    Date now = new Date();
    newBook.setCreatedDate(now);
    newBook.setUpdatedDate(now);
  }
}
