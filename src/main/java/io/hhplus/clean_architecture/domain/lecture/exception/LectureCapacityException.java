package io.hhplus.clean_architecture.domain.lecture.exception;

import io.hhplus.clean_architecture.common.exception.BaseException;

public class LectureCapacityException extends BaseException {
    public LectureCapacityException() {
        super(LectureExceptionEnums.MAX_CAPACITY);
    }
}
