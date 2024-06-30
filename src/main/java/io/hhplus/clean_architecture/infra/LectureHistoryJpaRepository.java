package io.hhplus.clean_architecture.infra;

import io.hhplus.clean_architecture.infra.entity.LectureHistoryEntity;
import io.hhplus.clean_architecture.infra.entity.LectureScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureHistoryJpaRepository extends JpaRepository<LectureHistoryEntity, Long> {

    Optional<LectureHistoryEntity> findLectureHistoryByLectureScheduleAndUserId(LectureScheduleEntity lectureScheduleEntity, Long userId);
}
