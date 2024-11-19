package com.airs.demo.controller;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import com.airs.demo.entity.User;
import com.airs.demo.service.LessonService;
import com.airs.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LessonController {

    private final LessonService lessonService;
    private final UserService userService;

    @Autowired
    public LessonController(LessonService lessonService, UserService userService) {
        this.lessonService = lessonService;
        this.userService = userService;
    }

    // レッスン完了時に呼び出されるメソッド
    @PostMapping("/completeLesson/{lessonId}")
    @ResponseBody  // JSON形式で返すために追加
    public Map<String, Object> completeLesson(@PathVariable Long lessonId, HttpSession session) {
    	System.out.println("Received lessonId: " + lessonId); 
    	Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("loggedInUser");

        if (user != null) {
            lessonService.completeLesson(lessonId, user.getId());
            System.out.println("Lesson completed for user ID: " + user.getId());

            
            userService.addExperiencePoints(user.getId(), 10);
            System.out.println("Experience points added for user ID: " + user.getId());

            
            // 更新されたユーザー情報を取得してセッションを更新
            User updatedUser = userService.findByName(user.getName());
            session.setAttribute("loggedInUser", updatedUser);
            
            response.put("success", true);
            response.put("updatedExperiencePoints", updatedUser.getExperiencePoints());
        } else {
            response.put("success", false);
            response.put("error", "ログインが必要です。");
        }

        return response;
    }


       
    @GetMapping("/lessonContent/{lessonId}")
    public String showContent(@PathVariable Long lessonId, HttpSession session, Model model) {
        // セッションから最新のUserオブジェクトを取得
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            // ユーザー情報をモデルに追加
            model.addAttribute("user", user);  // 最新のユーザー情報をモデルに追加
        } else {
            model.addAttribute("error", "ログインが必要です。");
            return "redirect:/login";  // ログイン画面にリダイレクト
        }
        model.addAttribute("lessonId", lessonId);

        // lessonIdに対応するレッスンの情報をモデルに追加する場合など
        // model.addAttribute("lesson", lessonService.getLessonById(lessonId));

        // 完了後にリダイレクトする
        return "newContentsTemplate";  
        }
}
