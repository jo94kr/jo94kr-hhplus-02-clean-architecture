package io.hhplus.clean_architecture.infra;

import io.hhplus.clean_architecture.domain.lecture.Lecture;
import io.hhplus.clean_architecture.domain.lecture.repository.LectureRepository;
import io.hhplus.clean_architecture.infra.mapper.LectureMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Page<Lecture> findAllLectureList(Pageable pageable) {
        return lectureJpaRepository.findAll(pageable).map(LectureMapper::toDomain);
    }

    @Override
    public Lecture findById(Long lectureId) {
        return LectureMapper.toDomain(lectureJpaRepository.findById(lectureId)
                .orElseThrow(EntityNotFoundException::new));
    }
}
