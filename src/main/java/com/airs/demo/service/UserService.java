package com.airs.demo.service;

import com.airs.demo.entity.User;        // Userエンティティをインポート
import com.airs.demo.repository.userdata.UserRepository;

import jakarta.transaction.Transactional;

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
    
    public void updateLevelImagePath(Long userId, String levelImagePath) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        user.setLevelImagePath(levelImagePath);
        userRepository.save(user);
    }

    
 // ユーザー名とパスワードでユーザーを検証するメソッド
    public User validateUser(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);  // DBからユーザーを検索
    }
    
 // ユーザー名でユーザーを検索するメソッド
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
    
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません: ID=" + id));
    }
        
        public String getLevelImagePath(User user) {
            int level = calculateLevel(user.getExperiencePoints());
            // レベルに応じた画像パスを返す
            switch (level) {
                case 1: return "/images/level1.jpg";
                case 2: return "/images/level2.jpg";
                case 3: return "/images/level3.jpg";
                case 4: return "/images/level4.jpg";
                default: return "/images/default.jpg";
            }
    }

        
        private int calculateLevel(int experiencePoints) {
            return (experiencePoints / 100) + 1; // 100ポイントごとにレベルが上がる
        }
    
    
	/* 特定のコンテンツや課題を完了したら、experience_pointsを加算 */
    @Transactional
    public void addExperiencePoints(Long userId, int points) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
        user.setExperiencePoints(user.getExperiencePoints() + points);
        userRepository.save(user);
        System.out.println("Updated Experience Points: " + user.getExperiencePoints());

    }
    
    

}


