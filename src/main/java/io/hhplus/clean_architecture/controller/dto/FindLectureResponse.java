package io.hhplus.clean_architecture.controller.dto;

import io.hhplus.clean_architecture.domain.lecture.Lecture;

public record FindLectureResponse(
        Long lectureId,
        String lectureName
) {

    public static FindLectureResponse of(Lecture lecture) {
        return new FindLectureResponse(lecture.getId(), lecture.getLectureName());
    }
}
