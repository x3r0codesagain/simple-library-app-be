package com.app.librarybackend.service.impl;

import com.app.librarybackend.exception.AppException;
import com.app.librarybackend.model.Author;
import com.app.librarybackend.model.Book;
import com.app.librarybackend.model.BorrowedBook;
import com.app.librarybackend.model.Member;
import com.app.librarybackend.model.enums.ErrorCodes;
import com.app.librarybackend.model.request.AddMemberRequest;
import com.app.librarybackend.repository.BookRepository;
import com.app.librarybackend.repository.BorrowedBookRepository;
import com.app.librarybackend.repository.MemberRepository;
import com.app.librarybackend.response.model.AuthorResponse;
import com.app.librarybackend.response.model.MemberResponse;
import com.app.librarybackend.service.MemberService;
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
public class MemberServiceImpl implements MemberService {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private BorrowedBookRepository borrowedBookRepository;

  @Autowired
  private Mapper mapper;

  @Override
  public MemberResponse addMember(AddMemberRequest addMemberRequest, boolean edit) {

    if (StringUtils.isBlank(addMemberRequest.getEmail()) || StringUtils.isBlank(addMemberRequest.getName()) ||
        StringUtils.isBlank(addMemberRequest.getPhone()) || StringUtils.isBlank(addMemberRequest.getGender())) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }

    Member savedMember = memberRepository.findByEmail(addMemberRequest.getEmail());

    if (Objects.nonNull(savedMember) && !edit) {
      throw new AppException(ErrorCodes.MEMBER_EXISTS.getMessage(), ErrorCodes.MEMBER_EXISTS);
    } else if (edit && Objects.isNull(savedMember)) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }


    Member member = mapper.map(addMemberRequest, Member.class);


    Date now = new Date();
    if (edit) {
      member.setId(savedMember.getId());
      member.setBorrowedBooks(savedMember.getBorrowedBooks());
      member.setCreatedDate(savedMember.getCreatedDate());
      member.setUpdatedDate(now);
    }

    memberRepository.save(member);

    MemberResponse response = mapper.map(member, MemberResponse.class);

    return response;
  }

  @Override
  public List<MemberResponse> getMembers() {
    List<Member> members = memberRepository.findAll();

    if (members.isEmpty()) {
      return new ArrayList<>();
    }

    List<MemberResponse> responses =
        members.stream().map(member -> mapper.map(member, MemberResponse.class))
            .collect(Collectors.toList());
    return responses;
  }

  @Override
  public MemberResponse deleteMember(String id) {
    Member member = memberRepository.findByEmail(id);

    if (Objects.isNull(member)) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }

    List<BorrowedBook> borrowedBooks = member.getBorrowedBooks();

    borrowedBooks.forEach(borrowedBook -> {
      borrowedBookRepository.deleteById(borrowedBook.getId());

      Book book = bookRepository.findById(borrowedBook.getBook().getId()). orElseGet(() -> null);

      if (Objects.nonNull(book)) {
        book.setUpdatedDate(new Date());
        book.setBorrowed(false);
        bookRepository.save(book);
      }
    });

    memberRepository.deleteById(member.getId());


    return mapper.map(member, MemberResponse.class);
  }

  @Override
  public MemberResponse getMember(String email) {
    Member member = memberRepository.findByEmail(email);

    if (Objects.isNull(member)) {
      throw new AppException(ErrorCodes.BAD_REQUEST.getMessage(), ErrorCodes.BAD_REQUEST);
    }

    return mapper.map(member, MemberResponse.class);
  }
}
