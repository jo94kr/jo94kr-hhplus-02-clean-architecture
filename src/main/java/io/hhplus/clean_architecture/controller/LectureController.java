package io.hhplus.clean_architecture.controller;

import io.hhplus.clean_architecture.controller.dto.ApplyLectureResponse;
import io.hhplus.clean_architecture.controller.dto.FindLectureResponse;
import io.hhplus.clean_architecture.controller.dto.FindLectureScheduleResponse;
import io.hhplus.clean_architecture.domain.lecture.LectureSchedule;
import io.hhplus.clean_architecture.domain.lecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;

    /**
     * 특강 신청
     */
    @PostMapping("/{lectureScheduleId}/apply")
    public ResponseEntity<ApplyLectureResponse> apply(@PathVariable(name = "lectureScheduleId") Long lectureScheduleId, @RequestBody Long userId) {
        LectureSchedule result = lectureService.apply(lectureScheduleId, userId);
        return ResponseEntity.ok(ApplyLectureResponse.of(result));
    }

    /**
     * 특강 목록
     */
    @GetMapping
    public ResponseEntity<Page<FindLectureResponse>> findAllLectureList(Pageable pageable) {
        return ResponseEntity.ok(lectureService.findAllLectureList(pageable).map(FindLectureResponse::of));
    }

    /**
     * 특강 스케쥴 목록
     */
    @GetMapping("/{lectureId}/schedule")
    public ResponseEntity<List<FindLectureScheduleResponse>> findAllLectureScheduleList(@PathVariable(name = "lectureId") Long lectureId) {
        return ResponseEntity.ok(lectureService.findAllLectureScheduleList(lectureId).stream()
                .map(FindLectureScheduleResponse::of)
                .toList());
    }

    /**
     * 특강 신청 여부 조회
     */
    @GetMapping("/application/{userId}")
    public ResponseEntity<Boolean> lectureApplicationCheck(@PathVariable(name = "userId") Long userId,
                                                           @RequestParam(name = "lectureScheduleId") Long lectureScheduleId) {
        return ResponseEntity.ok(lectureService.lectureApplicationCheck(userId, lectureScheduleId));
    }
}
