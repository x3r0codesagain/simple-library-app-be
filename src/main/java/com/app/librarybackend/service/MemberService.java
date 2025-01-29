package com.app.librarybackend.service;

import com.app.librarybackend.model.Member;
import com.app.librarybackend.model.request.AddMemberRequest;
import com.app.librarybackend.response.model.MemberResponse;

import java.util.List;

public interface MemberService {
  MemberResponse addMember(AddMemberRequest addMemberRequest, boolean edit);
  List<MemberResponse> getMembers();
  MemberResponse deleteMember(String id);
  MemberResponse getMember(String email);
}
