package com.airs.demo.repository.airsdb;

import com.airs.demo.entity.NewContents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewContentsRepository extends JpaRepository<NewContents, Long>{

}
