package com.airs.demo.repository.userdata;

import com.airs.demo.entity.CompletedLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompletedLessonRepository extends JpaRepository<CompletedLesson, Long> {
    List<CompletedLesson> findByUserId(Long userId);
    boolean existsByUserIdAndLessonId(Long userId, Long lessonId);
}
