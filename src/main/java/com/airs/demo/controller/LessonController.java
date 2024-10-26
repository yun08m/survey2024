package com.airs.demo.controller;

/*完了ボタンを押すときに呼び出されるエンドポイントを処理する*/
import com.airs.demo.entity.User;
import com.airs.demo.service.LessonService;
import com.airs.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

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
    public String completeLesson(@PathVariable Long lessonId, 
    							 @SessionAttribute(name ="loggedInUser", required = false) User user, Model model) {
    	
    	// ログインユーザー情報を出力して確認
    	 System.out.println("LoggedInUser: " + user.getName() + ", Experience Points: " + user.getExperiencePoints());
        // レッスン完了処理を行い、経験値を追加
        lessonService.completeLesson(lessonId, user.getId());
        userService.addExperiencePoints(user.getId(), 10);  // 10ポイント追加
        model.addAttribute("message", "レッスンを完了しました。経験値10ポイント獲得！");
        
        // 完了後に同じページにリダイレクトする
        return "redirect:/content/" + lessonId;
    }
}
