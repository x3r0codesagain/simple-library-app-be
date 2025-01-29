package com.app.librarybackend.controller;

import com.app.librarybackend.exception.AppException;
import com.app.librarybackend.model.request.AddMemberRequest;
import com.app.librarybackend.response.RestListResponse;
import com.app.librarybackend.response.RestSingleResponse;
import com.app.librarybackend.response.model.AuthorResponse;
import com.app.librarybackend.response.model.MemberResponse;
import com.app.librarybackend.service.MemberService;
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
@RequestMapping("api/v1/member")
public class MemberController {

  @Autowired
  private MemberService memberService;

  @PostMapping("/add")
  public RestSingleResponse<MemberResponse> addMember(@RequestParam boolean edit,
      @RequestBody AddMemberRequest addMemberRequest) {
    try {

      MemberResponse response = memberService.addMember(addMemberRequest, edit);

      return new RestSingleResponse<>(null, null, true, response);
    } catch (AppException e) {
      return new RestSingleResponse<>(e.getMessage(), e.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestSingleResponse<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }

  @GetMapping("/get")
  public RestListResponse<MemberResponse> getMembers() {
    try {
      List<MemberResponse> response = memberService.getMembers();
      return new RestListResponse<>(null, null, true, response);
    } catch (AppException appException) {
      return new RestListResponse<>(appException.getMessage(), appException.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestListResponse<>("Internal Server Error",
          HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }

  @GetMapping("/getOne")
  public RestSingleResponse<MemberResponse> getMember(@RequestParam String email) {
    try {
      MemberResponse response = memberService.getMember(email);
      return new RestSingleResponse<>(null, null, true, response);
    } catch (AppException appException) {
      return new RestSingleResponse<>(appException.getMessage(), appException.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestSingleResponse<>("Internal Server Error",
          HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }

  @PostMapping("/delete")
  public RestSingleResponse<MemberResponse> removeMember(@RequestParam String id) {
    try {

      MemberResponse response = memberService.deleteMember(id);

      return new RestSingleResponse<>(null, null, true, response);
    } catch (AppException e) {
      return new RestSingleResponse<>(e.getMessage(), e.getCode().name(), false, null);
    } catch (Exception e) {
      return new RestSingleResponse<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.name(), false, null);
    }
  }
}
