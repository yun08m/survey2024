package com.airs.demo.repository.airsdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airs.demo.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>{
}
