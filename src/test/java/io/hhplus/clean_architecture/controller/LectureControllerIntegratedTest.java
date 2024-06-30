package io.hhplus.clean_architecture.controller;

import io.hhplus.clean_architecture.IntegratedTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

// 테스트 격리를 위한 데이터 초기화
@Sql(scripts = "classpath:/db/create_lecture.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class LectureControllerIntegratedTest extends IntegratedTest {

    private static final String PATH = "/lectures";

    @Test
    @DisplayName("POST /{lectureScheduleId}/apply - 특강 신청")
    void apply() {
        // given
        Long userId = 1L;
        Long lectureScheduleId = 1L;

        // when
        ExtractableResponse<Response> response = post(PATH + "/" + lectureScheduleId + "/apply", userId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("정원이 30명인 특강 동시에 35명 신청 요청 호출")
    void concurrency_applyMoreThanCapacity() {
        // given
        // when
        int cnt = 35;
        CompletableFuture<Integer>[] futureArray = new CompletableFuture[cnt];
        for (int i = 0; i < cnt; i++) {
            final int userId = i + 1;
            futureArray[i] = CompletableFuture.supplyAsync(() -> {
                ExtractableResponse<Response> post = post(PATH + "/" + 1L + "/apply", userId);
                return post.statusCode();
            });
        }
        CompletableFuture.allOf(futureArray).join();

        // then
        List<Integer> failCnt = Arrays.stream(futureArray)
                .map(CompletableFuture::join)
                .filter(statusCode -> statusCode != HttpStatus.OK.value())
                .toList();

        assertThat(failCnt).hasSize(5);
    }

    @Test
    @DisplayName("동일한 사용자가 동일한 특강 동시 호출")
    void sameSpecialLectureByTheSameUser() {
        // given
        // when
        int cnt = 2;
        CompletableFuture<Integer>[] futureArray = new CompletableFuture[cnt];
        for (int i = 0; i < cnt; i++) {
            futureArray[i] = CompletableFuture.supplyAsync(() -> {
                ExtractableResponse<Response> post = post(PATH + "/" + 1L + "/apply", 1L);
                return post.statusCode();
            });
        }
        CompletableFuture.allOf(futureArray).join();

        // then
        List<Integer> failCnt = Arrays.stream(futureArray)
                .map(CompletableFuture::join)
                .filter(statusCode -> statusCode != HttpStatus.OK.value())
                .toList();

        assertThat(failCnt).hasSize(1);
    }

    @Test
    @DisplayName("특강 목록 조회")
    void findAllLecture() {
        // given
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("page", 0);
        requestParams.put("size", 1);

        // when
        ExtractableResponse<Response> response = get(PATH, requestParams);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("GET /{lectureId}/schedule - 특강 스케쥴 목록 조회")
    void findAllLectureSchedule() {
        // given
        Long lectureId = 1L;

        // when
        ExtractableResponse<Response> response = get(PATH + "/" + lectureId + "/schedule");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
