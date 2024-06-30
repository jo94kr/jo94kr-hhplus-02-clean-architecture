package io.hhplus.clean_architecture.controller.dto;

import io.hhplus.clean_architecture.domain.lecture.LectureSchedule;

import java.time.LocalDateTime;

public record FindLectureScheduleResponse(
        Long lectureScheduleId,
        String lectureName,
        LocalDateTime lectureDatetime,
        int capacity,
        int registerCnt
) {

    public static FindLectureScheduleResponse of(LectureSchedule lectureSchedule) {
        return new FindLectureScheduleResponse(lectureSchedule.getId(),
                lectureSchedule.getLecture().getLectureName(),
                lectureSchedule.getLectureDatetime(),
                lectureSchedule.getCapacity(),
                lectureSchedule.getRegisterCnt());
    }
}
