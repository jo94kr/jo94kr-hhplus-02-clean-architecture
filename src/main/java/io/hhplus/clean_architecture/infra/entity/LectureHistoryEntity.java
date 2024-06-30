package io.hhplus.clean_architecture.infra.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lecture_history")
public class LectureHistoryEntity extends BaseCreateDatetimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("특강 신청 내역 PK")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_schedule_id")
    @Comment("특강 스케쥴 PK")
    private LectureScheduleEntity lectureSchedule;

    @Column
    @Comment("사용자 PK")
    private Long userId;

    public LectureHistoryEntity(Long id, LectureScheduleEntity lectureScheduleEntity, Long userId) {
        this.id = id;
        this.lectureSchedule = lectureScheduleEntity;
        this.userId = userId;
    }

    public LectureHistoryEntity(LectureScheduleEntity lectureScheduleEntity, Long userId) {
        this.lectureSchedule = lectureScheduleEntity;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LectureHistoryEntity that = (LectureHistoryEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
