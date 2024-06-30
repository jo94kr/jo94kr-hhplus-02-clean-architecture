package io.hhplus.clean_architecture.infra;

import io.hhplus.clean_architecture.domain.lecture.LectureHistory;
import io.hhplus.clean_architecture.domain.lecture.LectureSchedule;
import io.hhplus.clean_architecture.domain.lecture.repository.LectureHistoryRepository;
import io.hhplus.clean_architecture.infra.entity.LectureHistoryEntity;
import io.hhplus.clean_architecture.infra.entity.LectureScheduleEntity;
import io.hhplus.clean_architecture.infra.mapper.LectureHistoryMapper;
import io.hhplus.clean_architecture.infra.mapper.LectureScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureHistoryRepositoryImpl implements LectureHistoryRepository {

    private final LectureHistoryJpaRepository lectureHistoryJpaRepository;

    @Override
    public LectureHistory save(LectureHistory lectureHistory) {
        return LectureHistoryMapper.toDomain(lectureHistoryJpaRepository.save(LectureHistoryMapper.toEntity(lectureHistory)));
    }

    @Override
    public Optional<LectureHistory> findLectureHistoryByLectureScheduleAndUserId(LectureSchedule lectureSchedule, Long userId) {
        LectureScheduleEntity lectureScheduleEntity = LectureScheduleMapper.toEntity(lectureSchedule);
        Optional<LectureHistoryEntity> optionalLectureHistoryEntity = lectureHistoryJpaRepository.findLectureHistoryByLectureScheduleAndUserId(lectureScheduleEntity, userId);
        return optionalLectureHistoryEntity.map(LectureHistoryMapper::toDomain);
    }
}
