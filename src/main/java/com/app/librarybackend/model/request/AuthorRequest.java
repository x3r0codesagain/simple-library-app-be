package com.app.librarybackend.model.request;

import com.app.librarybackend.model.BaseMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRequest extends BaseMongoEntity {
  private String id;
  private String name;
  private String gender;
}
