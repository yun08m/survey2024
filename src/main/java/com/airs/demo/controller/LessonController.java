package com.airs.demo.controller;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.airs.demo.entity.CompletedLesson;
import com.airs.demo.entity.Content;
import com.airs.demo.entity.User;
import com.airs.demo.repository.userdata.UserRepository;
import com.airs.demo.repository.userdata.CompletedLessonRepository;
import com.airs.demo.repository.airsdb.ContentRepository;
import com.airs.demo.service.LessonService;
import com.airs.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LessonController {

    private final LessonService lessonService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CompletedLessonRepository completedLessonRepository;
    private final ContentRepository contentRepository;

    @Autowired
    public LessonController(LessonService lessonService, UserService userService,
                            UserRepository userRepository, CompletedLessonRepository completedLessonRepository,
                            ContentRepository contentRepository) {
        this.lessonService = lessonService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.completedLessonRepository = completedLessonRepository;
        this.contentRepository = contentRepository;
    }

    @PostMapping("/completeLesson/{lessonId}")
    @ResponseBody
    public Map<String, Object> completeLesson(@PathVariable Long lessonId, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("loggedInUser");

        if (user != null) {
            // レッスンを完了
            lessonService.completeLesson(lessonId, user.getId());

            // 経験値を追加
            userService.addExperiencePoints(user.getId(), 10);

            // 更新されたユーザー情報をセッションに設定
            User updatedUser = userService.findByName(user.getName());
            session.setAttribute("loggedInUser", updatedUser);

            // 完了したレッスンIDリストを作成
            List<Long> completedContentIds = completedLessonRepository.findByUserId(user.getId())
                    .stream().map(CompletedLesson::getLessonId).collect(Collectors.toList());

            // レスポンスに必要な情報を追加
            response.put("success", true);
            response.put("updatedExperiencePoints", updatedUser.getExperiencePoints());
            response.put("completedContentIds", completedContentIds);
        } else {
            response.put("success", false);
            response.put("error", "ログインが必要です。");
        }

        return response;
    }

    @GetMapping("/lessonContent/{lessonId}")
    public String showContent(@PathVariable Long lessonId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            // ユーザー情報をモデルに追加
            model.addAttribute("user", user);

            // 完了したレッスンIDリストを追加
            List<Long> completedContentIds = completedLessonRepository.findByUserId(user.getId())
                    .stream().map(CompletedLesson::getLessonId).collect(Collectors.toList());
            model.addAttribute("completedContentIds", completedContentIds);
        } else {
            model.addAttribute("error", "ログインが必要です。");
            return "redirect:/login";
        }

        model.addAttribute("lessonId", lessonId);
        return "newContentsTemplate";
    }
}
