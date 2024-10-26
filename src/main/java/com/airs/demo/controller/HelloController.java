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
import com.airs.demo.repository.airsdb.ContentRepository;
import com.airs.demo.repository.airsdb.SubjectRepository;
import com.airs.demo.service.NewContentsService;
import com.airs.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class HelloController {
	
	@Autowired
    private NewContentsService newContentsService; // ここをクラスの先頭に移動
    public HelloController(NewContentsService newContentsService) {
        this.newContentsService = newContentsService;   
    }

    
 // 依存性注入
    @Autowired
    ContentRepository conRepository;

    @Autowired
    SubjectRepository subRepository;
    
    @Autowired
    private UserService userService; 


    

    @RequestMapping("/")
    private ModelAndView index(ModelAndView mav, HttpSession session) {
        mav.setViewName("index");
        
        String name = (String) session.getAttribute("loggedInUser");
        if (name != null) {
        	 User user = userService.findByName(name);
            mav.addObject("user", user); // ユーザー情報を追加
        }
        
        Iterable<Content> conlist = conRepository.findAll();
        Iterable<Subject> sublist = subRepository.findAll();
        List<NewContents> newContentsList = newContentsService.getAllContents();
        
        System.out.println(newContentsList);
        
        if (newContentsList == null || newContentsList.isEmpty()) {
            System.out.println("NewContents is empty or null.");
        }
        
        
        mav.addObject("condata", conlist);
        mav.addObject("subdata", sublist);
        mav.addObject("contents", newContentsList); // new_contentsのデータを渡す
        return mav;
    }

    @RequestMapping("/contents")
    private ModelAndView contents(ModelAndView mav) {
        mav.setViewName("contents");
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
        return "defaultPage";  // 空のテンプレートを返す
    }

    
    
}
