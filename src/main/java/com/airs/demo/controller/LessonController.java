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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, Object>> completeLesson(@PathVariable Long lessonId, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            // セッションからログイン中のユーザーを取得
            User user = (User) session.getAttribute("loggedInUser");

            if (user != null) {
                // レッスンを完了
                lessonService.completeLesson(lessonId, user.getId());

                // 経験値を追加
                userService.addExperiencePoints(user.getId(), 10);

                // 更新されたユーザー情報を取得し、セッションを更新
                User updatedUser = userService.findByName(user.getName());
                session.setAttribute("loggedInUser", updatedUser);

                // レベルを計算
                int level = updatedUser.getExperiencePoints() / 100;
                String levelImagePath = "/images/level" + (level + 1) + ".jpg";

                // データベースに保存
                userService.updateLevelImagePath(user.getId(), levelImagePath);

                // セッションも更新
                session.setAttribute("levelImagePath", levelImagePath);

                // レスポンスに必要な情報を追加
                response.put("success", true);
                response.put("updatedExperiencePoints", updatedUser.getExperiencePoints());
                response.put("levelImagePath", updatedUser.getLevelImagePath());

                return ResponseEntity.ok(response);
            } else {
                // ユーザーがログインしていない場合のエラー
                response.put("success", false);
                response.put("error", "ログインが必要です。");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            // 例外発生時のエラーハンドリング
            response.put("success", false);
            response.put("message", "処理中にエラーが発生しました: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
	/*１２２０
	 * @GetMapping("/lessonComplete") public String showLessonComplete(HttpSession
	 * session, Model model) { // セッションからデータを取得 User user = (User)
	 * session.getAttribute("loggedInUser"); String levelImagePath = (String)
	 * session.getAttribute("levelImagePath");
	 * 
	 * if (user != null) { model.addAttribute("message",
	 * "レッスンを完了しました。経験値10ポイント獲得！"); model.addAttribute("levelImagePath",
	 * levelImagePath); return "lessonComplete"; } else {
	 * model.addAttribute("error", "ログインが必要です。"); return "redirect:/login"; } }
	 */
    
    
    

    
	/*
	 * @PostMapping("/completeLesson/{lessonId}")
	 * 
	 * @ResponseBody public Map<String, Object> completeLesson(@PathVariable Long
	 * lessonId, HttpSession session) { Map<String, Object> response = new
	 * HashMap<>(); User user = (User) session.getAttribute("loggedInUser");
	 * 
	 * if (user != null) { // レッスンを完了 lessonService.completeLesson(lessonId,
	 * user.getId());
	 * 
	 * // 経験値を追加 userService.addExperiencePoints(user.getId(), 10);
	 * 
	 * // 更新されたユーザー情報をセッションに設定 User updatedUser =
	 * userService.findByName(user.getName()); session.setAttribute("loggedInUser",
	 * updatedUser);
	 * 
	 * // 完了したレッスンIDリストを作成 List<Long> completedContentIds =
	 * completedLessonRepository.findByUserId(user.getId())
	 * .stream().map(CompletedLesson::getLessonId).collect(Collectors.toList());
	 * 
	 * 
	 * 
	 * 
	 * 
	 * // レスポンスに必要な情報を追加 response.put("success", true);
	 * response.put("updatedExperiencePoints", updatedUser.getExperiencePoints());
	 * response.put("completedContentIds", completedContentIds);
	 * response.put("levelImagePath", userService.getLevelImagePath(user)); //
	 * レベル画像パス } else { response.put("success", false); response.put("error",
	 * "ログインが必要です。"); }
	 * 
	 * return response; }
	 */
    
    @GetMapping("/getUserData")
    @ResponseBody
    public Map<String, Object> getUserData(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("loggedInUser");

        if (user != null) {
            // ユーザーの経験値やレベル画像パスを取得
            int experiencePoints = user.getExperiencePoints();
            int level = experiencePoints / 100;
            String levelImagePath = (String) session.getAttribute("levelImagePath");
            if (levelImagePath == null) {
                // セッションにない場合のみ計算
                levelImagePath = "/images/level" + ((user.getExperiencePoints() / 100) + 1) + ".jpg";
                session.setAttribute("levelImagePath", levelImagePath);
            }
			/*
			 * String levelImagePath = "images/level" + (level + 1) + ".jpg"; // レベルごとの画像パス
			 */
            // レスポンスにデータを追加
            response.put("experiencePoints", experiencePoints);
            response.put("levelImagePath", levelImagePath);
        } else {
            response.put("error", "ユーザーがログインしていません");
        }

        return response;
    }

	/*
	 * @GetMapping("/lessonContent/{lessonId}") public String
	 * showContent(@PathVariable Long lessonId, HttpSession session, Model model) {
	 * User user = (User) session.getAttribute("loggedInUser"); if (user != null) {
	 * // ユーザー情報をモデルに追加 model.addAttribute("user", user);
	 * 
	 * // 完了したレッスンIDリストを追加 List<Long> completedContentIds =
	 * completedLessonRepository.findByUserId(user.getId())
	 * .stream().map(CompletedLesson::getLessonId).collect(Collectors.toList());
	 * model.addAttribute("completedContentIds", completedContentIds);
	 * 
	 * // ユーザーのレベルを計算 int level = user.getExperiencePoints() / 100;
	 * 
	 * // レベルごとの画像パスを決定 String levelImagePath = "images/level" + (level + 1)
	 * +".jpg"; System.out.println("Calculated levelImagePath: " + levelImagePath);
	 * 
	 * model.addAttribute("levelImagePath", levelImagePath);
	 * 
	 * 
	 * } else { model.addAttribute("error", "ログインが必要です。"); return "redirect:/login";
	 * }
	 * 
	 * model.addAttribute("lessonId", lessonId); return "newContentsTemplate"; }
	 */
}
