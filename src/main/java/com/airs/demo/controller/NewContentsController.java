package com.airs.demo.controller;

import com.airs.demo.entity.NewContents;
import com.airs.demo.service.NewContentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;


@Controller
public class NewContentsController {

    @Autowired
    private NewContentsService newContentsService;

    // 特定のIDに基づいてコンテンツを表示
    @GetMapping("/content/{id}")
    public String showContent(@PathVariable Long id, Model model) {
        // コンテンツを取得
        NewContents content = newContentsService.getContentById(id);

        // コンテンツが見つかった場合
        if (content != null) {
            model.addAttribute("content", content);
        } else {
            
        }
        
        return "NewContentTemplate"; // コンテンツテンプレートを返す
    }


    // すべてのコンテンツをリスト表示
    @GetMapping("/new-contents") // 新しいパスに変更
    public String listContents(Model model) {
        model.addAttribute("contents", newContentsService.getAllContents());
        return "contentList";
    }

}
