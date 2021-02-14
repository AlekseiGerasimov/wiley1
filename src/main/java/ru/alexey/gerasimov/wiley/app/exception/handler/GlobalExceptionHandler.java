package ru.alexey.gerasimov.wiley.app.exception.handler;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import ru.alexey.gerasimov.wiley.app.exception.BookNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> bookNotFound(BookNotFoundException exception, ServletWebRequest request) {
        return new ResponseEntity<>(ApiErrorResponse.builder()
                .title(exception.getMessage())
                .errorCode(ErrorCode.getCodeByExceptionType(BookNotFoundException.class))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Value
    @Builder
    private static class ApiErrorResponse {
        String title;
        int errorCode;
    }
}
