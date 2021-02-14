package ru.alexey.gerasimov.wiley.app.exception.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.alexey.gerasimov.wiley.app.exception.BookNotFoundException;
import ru.alexey.gerasimov.wiley.app.exception.ErrorCodeNotFoundException;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    BOOK_NOT_FOUND(BookNotFoundException.class, 1);

    private final Class<?> exceptionType;
    private final int code;

    public static int getCodeByExceptionType(Class<?> exceptionType) {
        return Arrays.stream(ErrorCode.values())
                .filter(errorCode -> errorCode.getExceptionType().equals(exceptionType))
                .map(ErrorCode::getCode)
                .findFirst()
                .orElseThrow(() -> new ErrorCodeNotFoundException
                        (String.format("Code not found for exception %s", exceptionType.getSimpleName())));
    }
}
