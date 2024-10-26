package com.airs.demo.service;

import com.airs.demo.entity.User;        // Userエンティティをインポート
import com.airs.demo.repository.userdata.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ユーザーを保存するメソッド
    public void saveUser(User user) {
        userRepository.save(user);  // UserRepositoryを使ってユーザーを保存
    }
    
 // ユーザー名とパスワードでユーザーを検証するメソッド
    public User validateUser(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);  // DBからユーザーを検索
    }
    
 // ユーザー名でユーザーを検索するメソッド
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
    
    
	/* 特定のコンテンツや課題を完了したら、experience_pointsを加算 */
    public void addExperiencePoints(Long userId, int points) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
        user.setExperiencePoints(user.getExperiencePoints() + points);
        userRepository.save(user);
    }

}


