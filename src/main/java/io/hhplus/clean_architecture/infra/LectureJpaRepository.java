package io.hhplus.clean_architecture.infra;

import io.hhplus.clean_architecture.infra.entity.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<LectureEntity, Long> {

}
