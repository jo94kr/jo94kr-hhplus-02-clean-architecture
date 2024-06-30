package io.hhplus.clean_architecture.domain.lecture.repository;

import io.hhplus.clean_architecture.domain.lecture.LectureHistory;
import io.hhplus.clean_architecture.domain.lecture.LectureSchedule;
import io.hhplus.clean_architecture.infra.entity.LectureHistoryEntity;
import io.hhplus.clean_architecture.infra.entity.LectureScheduleEntity;

import java.util.Optional;

public interface LectureHistoryRepository {

    LectureHistory save(LectureHistory lectureHistory);

    Optional<LectureHistory> findLectureHistoryByLectureScheduleAndUserId(LectureSchedule lectureSchedule, Long userId);
}
