package io.hhplus.clean_architecture.domain.lecture.service;

import io.hhplus.clean_architecture.domain.lecture.LectureSchedule;
import io.hhplus.clean_architecture.domain.lecture.repository.LectureHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplyLectureValidator implements LectureValidator {

    private final LectureHistoryRepository lectureHistoryRepository;

    @Override
    public boolean checkApplyLecture(LectureSchedule lectureSchedule, Long userId) {
        return lectureHistoryRepository.findLectureHistoryByLectureScheduleAndUserId(lectureSchedule, userId).isPresent();
    }
}
