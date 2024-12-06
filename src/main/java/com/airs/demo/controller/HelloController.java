package com.airs.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.airs.demo.entity.Content;
import com.airs.demo.entity.Subject;
import com.airs.demo.entity.User;
import com.airs.demo.entity.NewContents;
import com.airs.demo.entity.CompletedLesson; // CompletedLessonのエンティティをインポート
import com.airs.demo.repository.airsdb.ContentRepository;
import com.airs.demo.repository.airsdb.SubjectRepository;
import com.airs.demo.repository.userdata.CompletedLessonRepository; // CompletedLessonRepositoryのインポート
import com.airs.demo.service.NewContentsService;
import com.airs.demo.service.UserService;

import jakarta.servlet.http.HttpSession;


import java.util.List;

@Controller
public class HelloController {

    @Autowired
    private NewContentsService newContentsService;

    @Autowired
    private ContentRepository conRepository;

    @Autowired
    private SubjectRepository subRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CompletedLessonRepository completedLessonRepository; // CompletedLessonRepositoryを注入

    @RequestMapping("/")
    private ModelAndView index(ModelAndView mav, HttpSession session) {
        mav.setViewName("index");

        User user = (User) session.getAttribute("loggedInUser");

        if (user != null) {
            // ログイン中のユーザー情報をコンソールに表示
            System.out.println("Logged-in User Name: " + user.getName());
            System.out.println("User Experience Points from Session: " + user.getExperiencePoints());

            // レベルと残り経験値を計算
            int level = user.getExperiencePoints() / 100;
            int remainingExperience = user.getExperiencePoints() % 100;

            // 完了済みレッスンIDを取得
            List<Long> completedContentIds = completedLessonRepository.findByUserId(user.getId())
                    .stream()
                    .map(CompletedLesson::getLessonId)
                    .toList();

            // 完了済みレッスンIDをコンソールで確認
            System.out.println("Completed Content IDs: " + completedContentIds);

            // 必要なデータをビューに渡す
            mav.addObject("user", user);
            mav.addObject("level", level);
            mav.addObject("remainingExperience", remainingExperience);
            mav.addObject("completedContentIds", completedContentIds);
        } else {
            System.out.println("User is not logged in.");
        }

        // その他のデータをビューに渡す
        Iterable<Content> conlist = conRepository.findAll();
        Iterable<Subject> sublist = subRepository.findAll();
        List<NewContents> newContentsList = newContentsService.getAllContents();

        if (newContentsList == null || newContentsList.isEmpty()) {
            System.out.println("NewContents is empty or null.");
        }

        mav.addObject("condata", conlist);
        mav.addObject("subdata", sublist);
        mav.addObject("contents", newContentsList); // new_contentsのデータを渡す
        return mav;
    }

    @RequestMapping("/contents")
    private ModelAndView contents(ModelAndView mav, HttpSession session) {
        mav.setViewName("contents");

        // セッションからログインユーザーを取得
        User user = (User) session.getAttribute("loggedInUser");

        if (user != null) {
            // 完了済みのコンテンツIDを取得
            List<Long> completedContentIds = completedLessonRepository.findByUserId(user.getId())
                    .stream()
                    .map(CompletedLesson::getLessonId)
                    .toList();

            // 完了したコンテンツIDをモデルに渡す
            mav.addObject("completedContentIds", completedContentIds);
        }

        // その他のデータを取得してモデルに追加
        Iterable<Content> conlist = conRepository.findAll();
        Iterable<Subject> sublist = subRepository.findAll();
        List<NewContents> newContentsList = newContentsService.getAllContents();

        mav.addObject("condata", conlist);
        mav.addObject("subdata", sublist);
        mav.addObject("contents", newContentsList); // new_contentsのデータを渡す
        return mav;
    }


    @RequestMapping("/contact")
    private ModelAndView contact(ModelAndView mav) {
        mav.setViewName("contact");
        return mav;
    }

    @RequestMapping("/question")
    private ModelAndView question(ModelAndView mav) {
        mav.setViewName("question");
        return mav;
    }

    @RequestMapping("/chapter/{id}")
    public ModelAndView showChapter(@PathVariable("id") Long id, ModelAndView mav) {
        NewContents content = newContentsService.getContentById(id); // サービスメソッドを呼び出す
        mav.setViewName("newContentTemplate");
        mav.addObject("content", content);
        return mav;
    }

    @GetMapping("/defaultPage")
    public String defaultPage() {
        return "defaultPage"; // 空のテンプレートを返す
    }
}
