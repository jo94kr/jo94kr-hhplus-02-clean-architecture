package io.hhplus.clean_architecture.domain.lecture;

import lombok.Getter;

@Getter
public class Lecture {

    private final Long id;
    private final String lectureName;

    private Lecture(Long id, String lectureName) {
        if (lectureName == null) {
            throw new IllegalArgumentException("lectureName cannot be null");
        }
        this.id = id;
        this.lectureName = lectureName;
    }

    public static Lecture create(Long id, String lectureName) {
        return new Lecture(id, lectureName);
    }
}
