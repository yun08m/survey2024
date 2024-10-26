package com.airs.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.airs.demo.service.UserService;
import com.airs.demo.entity.User;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    // 登録フォーム表示用の GET リクエスト
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // フォーム送信後の POST リクエスト
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            // ユーザー情報をデータベースに保存
            userService.saveUser(user);
            return "redirect:/success"; // 成功時には success ページにリダイレクト
        } catch (Exception e) {
            // エラー発生時はエラーメッセージを表示
            model.addAttribute("message", "登録に失敗しました。もう一度お試しください。");
            return "register"; // 登録ページに戻る
        }
    }

    // 成功ページ表示用の GET リクエスト
    @GetMapping("/success")
    public String showSuccessPage() {
        return "success"; // success.html を表示
    }
}
