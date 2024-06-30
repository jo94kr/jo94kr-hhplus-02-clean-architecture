package io.hhplus.clean_architecture.common.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionInterface {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
