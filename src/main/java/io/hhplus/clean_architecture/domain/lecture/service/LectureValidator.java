package io.hhplus.clean_architecture.domain.lecture.service;

import io.hhplus.clean_architecture.domain.lecture.LectureSchedule;

public interface LectureValidator {

    boolean checkApplyLecture(LectureSchedule lectureSchedule, Long userId);
}
