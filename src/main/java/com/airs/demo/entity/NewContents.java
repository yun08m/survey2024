package com.airs.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "new_contents")  // テーブル名を指定
public class NewContents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    

    @Column(name = "title")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(name = "content_address", columnDefinition = "VARCHAR(255)")
    private String contentAddress;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // コンストラクタ
    public NewContents() {}

    public NewContents(String title, String body, String contentAddress, LocalDateTime createdAt) {
        this.title = title;
        this.body = body;
        this.contentAddress = contentAddress;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContentAddress() {
        return contentAddress;
    }

    public void setContentAddress(String contentAddress) {
        this.contentAddress = contentAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "NewContents{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", contentAddress='" + contentAddress + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}