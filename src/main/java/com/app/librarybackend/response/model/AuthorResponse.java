package com.app.librarybackend.response.model;

import com.app.librarybackend.model.BaseMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponse extends BaseResponse {
  private String name;
  private String gender;
}
