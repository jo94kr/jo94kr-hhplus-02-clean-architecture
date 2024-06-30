package io.hhplus.clean_architecture.infra;

import io.hhplus.clean_architecture.domain.lecture.Lecture;
import io.hhplus.clean_architecture.domain.lecture.LectureSchedule;
import io.hhplus.clean_architecture.infra.entity.LectureEntity;
import io.hhplus.clean_architecture.infra.entity.LectureScheduleEntity;
import io.hhplus.clean_architecture.domain.lecture.repository.LectureScheduleRepository;
import io.hhplus.clean_architecture.infra.mapper.LectureMapper;
import io.hhplus.clean_architecture.infra.mapper.LectureScheduleMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureScheduleRepositoryImpl implements LectureScheduleRepository {

    private final LectureScheduleJpaRepository lectureScheduleJpaRepository;

    @Override
    public LectureSchedule lockedFindById(Long lectureScheduleId) {
        return LectureScheduleMapper.toDomain(lectureScheduleJpaRepository.findLectureScheduleById(lectureScheduleId)
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public LectureSchedule findById(Long lectureScheduleId) {
        return LectureScheduleMapper.toDomain(lectureScheduleJpaRepository.findById(lectureScheduleId)
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<LectureSchedule> findAllLectureScheduleList(Lecture lecture) {
        return lectureScheduleJpaRepository.findAllByLecture(LectureMapper.toEntity(lecture)).stream()
                .map(LectureScheduleMapper::toDomain)
                .toList();
    }

    @Override
    public LectureSchedule save(LectureSchedule lectureSchedule) {
        return LectureScheduleMapper.toDomain(lectureScheduleJpaRepository.save(LectureScheduleMapper.toEntity(lectureSchedule)));
    }
}
