package io.hhplus.clean_architecture.infra.mapper;

import io.hhplus.clean_architecture.domain.lecture.Lecture;
import io.hhplus.clean_architecture.domain.lecture.LectureSchedule;
import io.hhplus.clean_architecture.infra.entity.LectureEntity;
import io.hhplus.clean_architecture.infra.entity.LectureScheduleEntity;

public class LectureScheduleMapper {
    public static LectureSchedule toDomain(LectureScheduleEntity lectureScheduleEntity) {
        return LectureSchedule.create(lectureScheduleEntity.getId(),
                LectureMapper.toDomain(lectureScheduleEntity.getLecture()),
                lectureScheduleEntity.getLectureDatetime(),
                lectureScheduleEntity.getRegisterCnt(),
                lectureScheduleEntity.getCapacity());
    }

    public static LectureScheduleEntity toEntity(LectureSchedule lectureSchedule) {
        return new LectureScheduleEntity(lectureSchedule.getId(),
                LectureMapper.toEntity(lectureSchedule.getLecture()),
                lectureSchedule.getLectureDatetime(),
                lectureSchedule.getRegisterCnt(),
                lectureSchedule.getCapacity());
    }
}
