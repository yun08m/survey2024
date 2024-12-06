package com.airs.demo.service;

import com.airs.demo.entity.Content;
import com.airs.demo.repository.airsdb.ContentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    private final ContentRepository contentRepository;

    @Autowired
    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    // すべてのコンテンツを取得するメソッド
    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }
}

