package com.app.librarybackend.response;

import com.app.librarybackend.response.model.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestSingleResponse<T extends BaseResponse> extends BaseRestResponse{
  private static final long serialVersionUID = -4787424083035235907L;

  private T value;

  public RestSingleResponse(String errorMessage, String errorCode, boolean success, T value) {
    super(errorMessage, errorCode, success);
    this.value = value;
  }
}
