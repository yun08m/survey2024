package com.airs.demo.service;

import com.airs.demo.entity.NewContents;
import com.airs.demo.repository.airsdb.NewContentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List; 




@Service
public class NewContentsService {

	
	@Autowired
	private NewContentsRepository newContentsRepository;

    // 全てのコンテンツを取得するメソッド
    public List<NewContents> getAllContents() {
        return newContentsRepository.findAll();
    }

    // ID からコンテンツを取得するメソッド
    public NewContents getContentById(Long id) {
        return newContentsRepository.findById(id).orElse(null);
    }
}
