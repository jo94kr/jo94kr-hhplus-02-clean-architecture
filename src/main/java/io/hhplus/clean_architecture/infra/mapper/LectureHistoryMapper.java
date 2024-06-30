package io.hhplus.clean_architecture.infra.mapper;

import io.hhplus.clean_architecture.domain.lecture.Lecture;
import io.hhplus.clean_architecture.domain.lecture.LectureHistory;
import io.hhplus.clean_architecture.infra.entity.LectureEntity;
import io.hhplus.clean_architecture.infra.entity.LectureHistoryEntity;

public class LectureHistoryMapper {
    public static LectureHistory toDomain(LectureHistoryEntity lectureHistoryEntity) {
        return LectureHistory.create(lectureHistoryEntity.getId(),
                LectureScheduleMapper.toDomain(lectureHistoryEntity.getLectureSchedule()),
                lectureHistoryEntity.getUserId());
    }

    public static LectureHistoryEntity toEntity(LectureHistory lectureHistory) {
        return new LectureHistoryEntity(lectureHistory.getId(),
                LectureScheduleMapper.toEntity(lectureHistory.getLectureSchedule()),
                lectureHistory.getUserId());
    }
}
