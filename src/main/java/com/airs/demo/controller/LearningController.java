package com.airs.demo.controller;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import com.airs.demo.entity.User;
import com.airs.demo.entity.Content;
import com.airs.demo.service.LessonService;
import com.airs.demo.service.UserService;
import com.airs.demo.service.ContentService;
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
    private final ContentService contentService;
    
    

    @Autowired
    public LearningController(LessonService lessonService, UserService userService,ContentService contentService) {
        this.lessonService = lessonService;
        this.userService = userService;
        this.contentService = contentService;
    }

    @GetMapping("/completeLesson/{lessonId}")
    public String completeLesson(@PathVariable Long lessonId, 
                                 @SessionAttribute("loggedInUser") User user, 
                                 HttpSession session,
                                 Model model) {
    	
        // レッスンを完了とマーク
        lessonService.completeLesson(lessonId, user.getId());
        
        // 経験値を加算（ここでは10ポイント）
        userService.addExperiencePoints(user.getId(), 10);
        
        // 最新のユーザー情報を取得
        User updatedUser = userService.findById(user.getId());
        user.setExperiencePoints(updatedUser.getExperiencePoints());
        
     // レベルを計算
        int level = updatedUser.getExperiencePoints() / 100;        
        // レベルごとの画像パスを計算
        String levelImagePath = "images/level" + (level + 1) + ".jpg";
        
        // セッションに新しい画像パスを保存
        session.setAttribute("levelImagePath", levelImagePath);
        
        // 完了メッセージを設定
        model.addAttribute("message", "レッスンを完了しました。経験値10ポイント獲得！");
        model.addAttribute("levelImagePath", levelImagePath);
        
        System.out.println("levelImagePath: " + session.getAttribute("levelImagePath"));


        
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
    @GetMapping("/user-contents") 
    public String getUserContents(@SessionAttribute("loggedInUser") User user, Model model) {
        // すべてのコンテンツを取得
    	 List<Content> allContents = contentService.getAllContents();
    	List<Long> completedContentIds = lessonService.getCompletedContentIds(user.getId());
    	List<String> completedContentIdsAsString = completedContentIds.stream()
    	                                                              .map(String::valueOf)
    	                                                              .toList();
    	model.addAttribute("completedContentIds", completedContentIdsAsString);

        if (completedContentIds == null) {
            completedContentIds = List.of();
        }
        

        // モデルにデータを追加
        model.addAttribute("contents", allContents);
        model.addAttribute("completedContentIds", completedContentIds);

        return "contents"; // 完了状況を表示するテンプレート
    }
    
    


    
}
