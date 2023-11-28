package com.capstone.fxteam.constant.dto;

import com.capstone.fxteam.constant.enums.CustomResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.capstone.fxteam.constant.enums.CustomResponseStatus.SUCCESS;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonPropertyOrder({"isSuccess", "code", "message", "data"})
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private Boolean isSuccess;
    private String message;
    private int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> ApiResponse<T> createSuccess(T data, CustomResponseStatus status) {
        return new ApiResponse<>(true, status.getMessage(), status.getCode(), data);
    }

    public static ApiResponse<String> createSuccessWithNoContent(CustomResponseStatus status) {
        return new ApiResponse<>(true, status.getMessage(), status.getCode(), null);
    }

    //Hibernate Validator에 의해 유효하지 않은 데이터로 인해 API 호출이 거불될때 반환
    public static ApiResponse<Map<String, String>> createFail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiResponse<>(false, "유효하지 않은 데이터입니다.", HttpStatus.BAD_REQUEST.value(), errors);
    }

    // 예외 발생으로 API 호출 실패시 반환
    public static ApiResponse<String> createError(CustomResponseStatus status) {
        return new ApiResponse<>(status.isSuccess(), status.getMessage(), status.getCode(), null);
    }

    private ApiResponse(CustomResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }

    // 요청에 성공한 경우
    private ApiResponse(T data) {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.data = data;
    }
}
