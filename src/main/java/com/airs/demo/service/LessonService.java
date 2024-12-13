package com.airs.demo.service;

import com.airs.demo.repository.userdata.LessonRepository;
import com.airs.demo.repository.userdata.UserRepository;
import com.airs.demo.repository.airsdb.ContentRepository;
import com.airs.demo.repository.userdata.CompletedLessonRepository;
import com.airs.demo.entity.CompletedLesson;
import com.airs.demo.entity.Content;
import com.airs.demo.entity.User;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class LessonService {
    private final ContentRepository contentRepository;
    private final CompletedLessonRepository completedLessonRepository;

    @Autowired
    public LessonService(ContentRepository contentRepository, CompletedLessonRepository completedLessonRepository) {
        this.contentRepository = contentRepository;
        this.completedLessonRepository = completedLessonRepository;
    }

    public List<Content> getAllContents() {
        return contentRepository.findAll(); // 全コンテンツを取得
    }

    public List<Long> getCompletedContentIds(Long userId) {
    	 List<Long> completedContentIds = completedLessonRepository
    		        .findByUserId(userId)
    		        .stream()
    		        .map(CompletedLesson::getLessonId)
    		        .toList();
    		    System.out.println("Completed Content IDs: " + completedContentIds);
    		    return completedContentIds;

    	
          }

    public List<Content> getIncompleteContents(Long userId) {
        List<Long> completedIds = getCompletedContentIds(userId);
        return contentRepository.findAll()
                                .stream()
                                .filter(content -> !completedIds.contains(content.getId()))
                                .collect(Collectors.toList());
    }
    
    @Transactional
    public void completeLesson(Long lessonId, Long userId) {
        // 既に完了している場合はスキップ
        if (completedLessonRepository.existsByUserIdAndLessonId(userId, lessonId)) {
            return;
        }

        // 完了情報を保存
        CompletedLesson completedLesson = new CompletedLesson();
        completedLesson.setLessonId(lessonId);
        completedLesson.setUserId(userId);
        
        

        completedLessonRepository.save(completedLesson);
}
}
