package com.airs.demo.service;

import com.airs.demo.repository.userdata.LessonRepository;
import com.airs.demo.repository.userdata.UserRepository;
import com.airs.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository, UserRepository userRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void completeLesson(Long lessonId, Long userId) {
        // レッスンを完了したことを記録する (必要に応じて)
        // 例えば、完了したレッスンを特定のテーブルに保存したり、ログを記録する

        // 完了したユーザーの情報を取得
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // 必要に応じて、レッスン完了のステータスなどを保存する処理を追加
    }

    // 必要に応じてその他のサービスメソッドを追加
}
