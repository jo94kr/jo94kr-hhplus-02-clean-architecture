package io.hhplus.clean_architecture.infra.mapper;

import io.hhplus.clean_architecture.domain.lecture.Lecture;
import io.hhplus.clean_architecture.infra.entity.LectureEntity;

public class LectureMapper {
    public static Lecture toDomain(LectureEntity lectureEntity) {
        return Lecture
                .builder()
                .id(lectureEntity.getId())
                .lectureName(lectureEntity.getLectureName())
                .build();
    }

    public static LectureEntity toEntity(Lecture lecture) {
        return new LectureEntity(lecture.getId(), lecture.getLectureName());
    }
}
