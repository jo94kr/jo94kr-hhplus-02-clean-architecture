package io.hhplus.clean_architecture.infra;

import io.hhplus.clean_architecture.infra.entity.LectureEntity;
import io.hhplus.clean_architecture.infra.entity.LectureScheduleEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface LectureScheduleJpaRepository extends JpaRepository<LectureScheduleEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LectureScheduleEntity> findLectureScheduleById(Long lectureScheduleId);

    List<LectureScheduleEntity> findAllByLecture(LectureEntity lectureEntity);
}
