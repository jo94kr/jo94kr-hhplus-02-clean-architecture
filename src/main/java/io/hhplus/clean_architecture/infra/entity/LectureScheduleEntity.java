package io.hhplus.clean_architecture.infra.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lecture_schedule")
public class LectureScheduleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("특강 스케쥴 PK")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    @Comment("특강 PK")
    private LectureEntity lecture;

    @Column(nullable = false)
    @Comment("특강 시작일시")
    private LocalDateTime lectureDatetime;

    @Column(nullable = false)
    @Comment("신청 인원")
    private int registerCnt = 0;

    @Column(nullable = false)
    @Comment("정원")
    private int capacity = 30;

    public LectureScheduleEntity(Long id,
                                 LectureEntity lectureEntity,
                                 LocalDateTime lectureDatetime,
                                 int registerCnt,
                                 int capacity) {
        this.id = id;
        this.lecture = lectureEntity;
        this.lectureDatetime = lectureDatetime;
        this.registerCnt = registerCnt;
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LectureScheduleEntity lectureScheduleEntity = (LectureScheduleEntity) o;
        return Objects.equals(id, lectureScheduleEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
