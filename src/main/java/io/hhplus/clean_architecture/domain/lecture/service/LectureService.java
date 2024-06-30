package io.hhplus.clean_architecture.domain.lecture.service;

import io.hhplus.clean_architecture.domain.lecture.Lecture;
import io.hhplus.clean_architecture.domain.lecture.LectureHistory;
import io.hhplus.clean_architecture.domain.lecture.LectureSchedule;
import io.hhplus.clean_architecture.domain.lecture.repository.LectureHistoryRepository;
import io.hhplus.clean_architecture.domain.lecture.repository.LectureRepository;
import io.hhplus.clean_architecture.domain.lecture.repository.LectureScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = {Exception.class})
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureScheduleRepository lectureScheduleRepository;
    private final LectureHistoryRepository lectureHistoryRepository;

    private final LectureValidator lectureValidator;


    @Transactional(rollbackFor = {Exception.class})
    public LectureSchedule apply(Long lectureScheduleId, Long userId) {
        // 특강 스케쥴 조회
        LectureSchedule lectureSchedule = lectureScheduleRepository.lockedFindById(lectureScheduleId);

        // 특강 신청
        LectureSchedule apply = lectureSchedule.apply(lectureValidator, userId);

        // 특강 내역 등록
        lectureHistoryRepository.save(LectureHistory.create(null, lectureScheduleRepository.save(apply), userId));
        return lectureSchedule;
    }

    public Page<Lecture> findAllLectureList(Pageable pageable) {
        return lectureRepository.findAllLectureList(pageable);
    }

    public List<LectureSchedule> findAllLectureScheduleList(Long lectureId) {
        // 특강 조회
        Lecture lecture = lectureRepository.findById(lectureId);

        // 특강 스케쥴 목록 조회
        return lectureScheduleRepository.findAllLectureScheduleList(lecture);
    }

    public Boolean lectureApplicationCheck(Long userId, Long lectureScheduleId) {
        // 특강 스케쥴 조회
        LectureSchedule lectureSchedule = lectureScheduleRepository.findById(lectureScheduleId);

        // 사용자 특강 조회
        return lectureHistoryRepository.findLectureHistoryByLectureScheduleAndUserId(lectureSchedule, userId).isEmpty();
    }
}
