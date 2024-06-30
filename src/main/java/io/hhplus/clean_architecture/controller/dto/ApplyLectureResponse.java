package io.hhplus.clean_architecture.controller.dto;

import io.hhplus.clean_architecture.domain.lecture.Lecture;
import io.hhplus.clean_architecture.domain.lecture.LectureSchedule;

public record ApplyLectureResponse(
        Long lectureId,
        Long lectureScheduleId,
        String name
) {

    public static ApplyLectureResponse of(LectureSchedule lectureSchedule) {
        Lecture lecture = lectureSchedule.getLecture();
        return new ApplyLectureResponse(lecture.getId(), lectureSchedule.getId(), lecture.getLectureName());
    }
}
