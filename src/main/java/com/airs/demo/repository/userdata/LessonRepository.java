package com.airs.demo.repository.userdata;


import com.airs.demo.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    
    // 特定のユーザーに関連するレッスンを検索するためのメソッド
    List<Lesson> findByUserId(Long userId);

    // 完了していないレッスンを検索するためのメソッド
    List<Lesson> findByUserIdAndCompletedFalse(Long userId);

    // 完了済みのレッスンを検索するためのメソッド
    List<Lesson> findByUserIdAndCompletedTrue(Long userId);
}
