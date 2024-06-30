package io.hhplus.clean_architecture.domain.lecture.service;

import io.hhplus.clean_architecture.domain.lecture.Lecture;
import io.hhplus.clean_architecture.domain.lecture.LectureHistory;
import io.hhplus.clean_architecture.domain.lecture.LectureSchedule;
import io.hhplus.clean_architecture.domain.lecture.exception.AlreadyExistException;
import io.hhplus.clean_architecture.domain.lecture.exception.LectureCapacityException;
import io.hhplus.clean_architecture.domain.lecture.exception.LectureDateException;
import io.hhplus.clean_architecture.domain.lecture.exception.LectureExceptionEnums;
import io.hhplus.clean_architecture.domain.lecture.repository.LectureHistoryRepository;
import io.hhplus.clean_architecture.domain.lecture.repository.LectureScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @InjectMocks
    private LectureService lectureService;

    @Mock
    private LectureHistoryRepository lectureHistoryRepository;

    @Mock
    private LectureScheduleRepository lectureScheduleRepository;

    @Mock
    private LectureValidator lectureValidator;

    private LectureSchedule defaultLectureSchedule;

    @BeforeEach
    void setUp() {
        // 특강 기본 세팅
        Lecture defaultLecture = Lecture.create(1L, "항해 플러스 백엔드");
        defaultLectureSchedule = LectureSchedule.create(1L,
                defaultLecture,
                LocalDateTime.of(2024, 6, 25, 12, 0, 0),
                0,
                30);
    }

    @Test
    @DisplayName("아이디로 특강 신청 성공")
    void apply() {
        // given
        Long lectureScheduleId = 1L;
        Long userId = 1L;

        // when
        when(lectureScheduleRepository.lockedFindById(lectureScheduleId)).thenReturn(defaultLectureSchedule);
        when(lectureValidator.checkApplyLecture(defaultLectureSchedule, userId)).thenReturn(false);
        lectureService.apply(lectureScheduleId, userId);

        // then
        verify(lectureScheduleRepository).lockedFindById(lectureScheduleId);
        verify(lectureHistoryRepository).save(any());
    }

    @Test
    @DisplayName("특강 중복 신청 불가 예외 발생")
    void applyDuplicateLecture() {
        // given
        Long lectureScheduleId = 1L;
        Long userId = 1L;

        // when
        when(lectureScheduleRepository.lockedFindById(lectureScheduleId)).thenReturn(defaultLectureSchedule);
        when(lectureValidator.checkApplyLecture(defaultLectureSchedule, userId)).thenReturn(true);

        // then
        assertThatThrownBy(() -> lectureService.apply(userId, userId))
                .isInstanceOf(AlreadyExistException.class)
                .hasMessageContaining(LectureExceptionEnums.ALREADY_EXISTS.getMessage());
    }

    @Test
    @DisplayName("특강 정원 30명 초과 예외 발생")
    void lectureCapacity() {
        // given
        Long lectureScheduleId = 1L;
        Long userId = 1L;
        Lecture lecture = Lecture.create(1L, "항해 플러스 백엔드");
        LectureSchedule lectureSchedule = LectureSchedule.create(1L,
                lecture,
                LocalDateTime.now(),
                30,
                30);

        // when
        when(lectureScheduleRepository.lockedFindById(lectureScheduleId)).thenReturn(lectureSchedule);
        when(lectureValidator.checkApplyLecture(lectureSchedule, userId)).thenReturn(false);

        // then
        assertThatThrownBy(() -> lectureService.apply(lectureScheduleId, userId))
                .isInstanceOf(LectureCapacityException.class)
                .hasMessageContaining(LectureExceptionEnums.MAX_CAPACITY.getMessage());
    }

    @Test
    @DisplayName("특강 시작일 이전에 특강 신청 시도 시 예외 발생")
    void applyLectureBeforeStartDate() {
        // given
        Long lectureId = 1L;
        Long userId = 1L;
        Lecture lecture = Lecture.create(lectureId, "항해 플러스 백엔드");
        LectureSchedule lectureSchedule = LectureSchedule.create(1L,
                lecture,
                LocalDateTime.now().plusDays(1),
                0,
                30);

        // when
        when(lectureScheduleRepository.lockedFindById(lectureId)).thenReturn(lectureSchedule);
        when(lectureValidator.checkApplyLecture(lectureSchedule, userId)).thenReturn(false);

        // then
        assertThatThrownBy(() -> lectureService.apply(userId, userId))
                .isInstanceOf(LectureDateException.class)
                .hasMessageContaining(LectureExceptionEnums.BEFORE_START_DATE.getMessage());
    }

    @Test
    @DisplayName("이미 신청한 특강 신청 여부 조회 시 false 반환")
    void checkAlreadyAppliedLectureFalse() {
        // given
        Long lectureScheduleId = 1L;
        Long userId = 1L;
        Lecture lecture = Lecture.create(1L, "항해 플러스 백엔드");
        LectureSchedule lectureSchedule = LectureSchedule.create(1L,
                lecture,
                LocalDateTime.now().plusDays(1),
                1,
                30);

        LectureHistory lectureHistory = LectureHistory.create(null, lectureSchedule, userId);

        // when
        when(lectureScheduleRepository.findById(lectureScheduleId)).thenReturn(lectureSchedule);
        when(lectureHistoryRepository.findLectureHistoryByLectureScheduleAndUserId(lectureSchedule, userId)).thenReturn(Optional.of(lectureHistory));
        Boolean result = lectureService.lectureApplicationCheck(userId, lectureScheduleId);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("이미 신청한 특강 신청 여부 조회 시 false 반환")
    void checkAlreadyAppliedLectureTrue() {
        // given
        Long lectureScheduleId = 1L;
        Long userId = 1L;
        Lecture lecture = Lecture.create(1L, "항해 플러스 백엔드");
        LectureSchedule lectureSchedule = LectureSchedule.create(1L,
                lecture,
                LocalDateTime.now().plusDays(1),
                1,
                30);

        // when
        when(lectureScheduleRepository.findById(lectureScheduleId)).thenReturn(lectureSchedule);
        when(lectureHistoryRepository.findLectureHistoryByLectureScheduleAndUserId(lectureSchedule, userId))
                .thenReturn(Optional.of(LectureHistory.create(null, lectureSchedule, userId)));
        Boolean result = lectureService.lectureApplicationCheck(userId, lectureScheduleId);

        // then
        assertThat(result).isFalse();
    }
}
