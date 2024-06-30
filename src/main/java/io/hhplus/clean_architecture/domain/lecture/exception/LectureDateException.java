package io.hhplus.clean_architecture.domain.lecture.exception;

import io.hhplus.clean_architecture.common.exception.BaseException;

public class LectureDateException extends BaseException {
    public LectureDateException() {
        super(LectureExceptionEnums.BEFORE_START_DATE);
    }
}
