package io.hhplus.clean_architecture.domain.lecture;

import io.hhplus.clean_architecture.domain.lecture.exception.AlreadyExistException;
import io.hhplus.clean_architecture.domain.lecture.exception.LectureCapacityException;
import io.hhplus.clean_architecture.domain.lecture.exception.LectureDateException;
import io.hhplus.clean_architecture.domain.lecture.repository.LectureRepository;
import io.hhplus.clean_architecture.domain.lecture.service.LectureValidator;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LectureScheduleTest {

    @Mock
    private LectureValidator lectureValidator;

    @Test
    @DisplayName("특강 신청 정상 처리")
    void apply() {
        // given
        Long userId = 1L;
        Lecture lecture = Lecture.create(1L, "항해 플러스 백엔드");
        LectureSchedule lectureSchedule = LectureSchedule.create(1L,
                lecture,
                LocalDateTime.of(2024, 6, 25, 13, 0, 0),
                0,
                30);

        // when
        LectureSchedule apply = lectureSchedule.apply(lectureValidator, userId);

        // then
        assertThat(apply.getRegisterCnt()).isEqualTo(1);
    }

    @Test
    @DisplayName("이미 신청한 특강 신청")
    void alreadyExistLecture() {
        // given
        Long userId = 1L;
        Lecture lecture = Lecture.create(1L, "항해 플러스 백엔드");
        LectureSchedule lectureSchedule = LectureSchedule.create(1L,
                lecture,
                LocalDateTime.of(2024, 6, 25, 13, 0, 0),
                0,
                30);

        // when
        when(lectureValidator.checkApplyLecture(lectureSchedule, userId)).thenReturn(true);
        ThrowableAssert.ThrowingCallable throwingCallable = () -> lectureSchedule.apply(lectureValidator, userId);

        // then
        assertThatExceptionOfType(AlreadyExistException.class).isThrownBy(throwingCallable);
    }

    @Test
    @DisplayName("시작일 이전 특강 신청")
    void applyLectureBeforeStartDate() {
        // given
        Long userId = 1L;
        Lecture lecture = Lecture.create(1L, "항해 플러스 백엔드");
        LectureSchedule lectureSchedule = LectureSchedule.create(1L,
                lecture,
                LocalDateTime.now().plusDays(1),
                0,
                30);

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> lectureSchedule.apply(lectureValidator, userId);

        // then
        assertThatExceptionOfType(LectureDateException.class).isThrownBy(throwingCallable);
    }

    @Test
    @DisplayName("정원 초과 상태에서 특강 신청")
    void lectureCapacityOver() {
        // given
        Long userId = 1L;
        Lecture lecture = Lecture.create(1L, "항해 플러스 백엔드");
        LectureSchedule lectureSchedule = LectureSchedule.create(1L,
                lecture,
                LocalDateTime.of(2024, 6, 25, 13, 0, 0),
                30,
                30);

        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> lectureSchedule.apply(lectureValidator, userId);

        // then
        assertThatExceptionOfType(LectureCapacityException.class).isThrownBy(throwingCallable);
    }
}
