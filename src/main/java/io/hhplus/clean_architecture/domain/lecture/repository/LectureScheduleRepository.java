package io.hhplus.clean_architecture.domain.lecture.repository;

import io.hhplus.clean_architecture.domain.lecture.Lecture;
import io.hhplus.clean_architecture.domain.lecture.LectureSchedule;
import io.hhplus.clean_architecture.infra.entity.LectureEntity;
import io.hhplus.clean_architecture.infra.entity.LectureScheduleEntity;

import java.util.List;

public interface LectureScheduleRepository {

    /**
     * 특강 스케쥴 조회 - lock
     */
    LectureSchedule lockedFindById(Long lectureScheduleId);

    LectureSchedule findById(Long lectureScheduleId);

    List<LectureSchedule> findAllLectureScheduleList(Lecture lecture);

    LectureSchedule save(LectureSchedule lectureSchedule);
}
