package io.hhplus.clean_architecture.controller.dto;

import io.hhplus.clean_architecture.domain.entity.LectureSchedule;

import java.time.LocalDateTime;

public record FindLectureScheduleResDto(
        Long lectureScheduleId,
        String lectureName,
        LocalDateTime lectureDatetime,
        int capacity,
        int registerCnt
) {

    public static FindLectureScheduleResDto of(LectureSchedule lectureSchedule) {
        return new FindLectureScheduleResDto(lectureSchedule.getId(),
                lectureSchedule.getLecture().getLectureName(),
                lectureSchedule.getLectureDatetime(),
                lectureSchedule.getCapacity(),
                lectureSchedule.getRegisterCnt());
    }
}