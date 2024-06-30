package io.hhplus.clean_architecture.domain.lecture.repository;

import io.hhplus.clean_architecture.domain.lecture.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LectureRepository {

    /**
     * 특강 조회
     */
    Lecture findById(Long lectureId);

    /**
     * 특강 목록 조회
     */
    Page<Lecture> findAllLectureList(Pageable pageable);

}
