package com.airs.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.airs.demo.entity.User;
import com.airs.demo.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;  // UserServiceをインジェクション

    // GETメソッド
    @GetMapping("/login")
    public String getLogin(Model model) {
        // login.htmlに画面遷移
        return "login";
    }

    // POSTメソッド
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpSession session, Model model) {
    	  System.out.println("Request Parameters: " + user.getName() + ", " + user.getPassword());
        System.out.println("ログインを試みています: " + user.getName());
        // ユーザーが正しいかどうかチェック
        User loggedInUser = userService.validateUser(user.getName(), user.getPassword());

        if (loggedInUser != null) {
            // ログイン成功の場合、ユーザーオブジェクト全体をセッションに保存
            session.setAttribute("loggedInUser", loggedInUser);
            System.out.println("User Experience Points: " + loggedInUser.getExperiencePoints());
            return "redirect:/";  // ログイン成功後、トップページにリダイレクト
        } else {
            System.out.println("ログイン失敗: " + user.getName());
            // ログイン失敗の場合、エラーメッセージを表示
            model.addAttribute("error", "ユーザー名またはパスワードが正しくありません");
            return "login";  // ログインページを再表示
        }

    }
}
