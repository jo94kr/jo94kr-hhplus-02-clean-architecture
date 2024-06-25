package io.hhplus.clean_architecture.repository;

import io.hhplus.clean_architecture.domain.Lecture;
import io.hhplus.clean_architecture.domain.LectureHistory;

import java.util.Optional;

public interface LectureHistoryRepository {

    LectureHistory save(LectureHistory lectureHistory);

    Optional<LectureHistory> findLectureHistoryByLectureAndUserId(Lecture lecture, Long userId);
}
