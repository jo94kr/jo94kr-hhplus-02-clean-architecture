package io.hhplus.clean_architecture.domain.lecture.exception;

import io.hhplus.clean_architecture.common.exception.ExceptionInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LectureExceptionEnums implements ExceptionInterface {

    ALREADY_EXISTS(HttpStatus.CONFLICT, "ALREADY_EXISTS", "lecture is already exist."),
    MAX_CAPACITY(HttpStatus.BAD_REQUEST, "MAX_CAPACITY", "lecture capacity exceeded."),
    BEFORE_START_DATE(HttpStatus.BAD_REQUEST, "BEFORE_START_DATE", "before the start date."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
