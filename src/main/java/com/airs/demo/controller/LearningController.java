package com.airs.demo.controller;

import com.airs.demo.entity.User;
import com.airs.demo.service.LessonService;
import com.airs.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class LearningController {

    private final LessonService lessonService;
    private final UserService userService;

    @Autowired
    public LearningController(LessonService lessonService, UserService userService) {
        this.lessonService = lessonService;
        this.userService = userService;
    }

    @GetMapping("/completeLesson/{lessonId}")
    public String completeLesson(@PathVariable Long lessonId, 
                                 @SessionAttribute("loggedInUser") User user, 
                                 Model model) {
    	
        // レッスンを完了とマーク
        lessonService.completeLesson(lessonId, user.getId());
        
        // 経験値を加算（ここでは10ポイント）
        userService.addExperiencePoints(user.getId(), 10);
        
        // 完了メッセージを設定
        model.addAttribute("message", "レッスンを完了しました。経験値10ポイント獲得！");
        
        return "lessonComplete"; // レッスン完了ページを表示
    }
    @GetMapping("/userProfile")
    public String getUserProfile(@SessionAttribute("loggedInUser") User user, Model model) {
    	System.out.println("User Name: " + user.getName());
        System.out.println("Experience Points: " + user.getExperiencePoints());
        
        int level = user.getExperiencePoints() / 100; // レベルを計算
        int remainingExperience = user.getExperiencePoints() % 100; // 余りの経験値を計算

        model.addAttribute("level", level);
        model.addAttribute("remainingExperience", remainingExperience);
        model.addAttribute("user", user);
        return "userProfile"; // ビュー名を指定
    }

    
}
