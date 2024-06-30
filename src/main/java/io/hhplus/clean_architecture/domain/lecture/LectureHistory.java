package io.hhplus.clean_architecture.domain.lecture;

import lombok.Getter;

@Getter
public class LectureHistory {

    private final Long id;
    private final LectureSchedule lectureSchedule;
    private final Long userId;

    private LectureHistory(Long id, LectureSchedule lectureSchedule, Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        this.id = id;
        this.lectureSchedule = lectureSchedule;
        this.userId = userId;
    }

    public static LectureHistory create(Long id, LectureSchedule lectureSchedule, Long userId) {
        return new LectureHistory(id, lectureSchedule, userId);
    }
}
