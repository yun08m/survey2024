package com.airs.demo.repository.userdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.airs.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 名前とパスワードでユーザーを検索する
    User findByNameAndPassword(String name, String password);
    
    // ユーザー名でユーザーを検索する（メソッド名を修正）
    User findByName(String name);
}
