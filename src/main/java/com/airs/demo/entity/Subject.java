package com.airs.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subject")
public class Subject {
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	@Column(name = "subject_id")
	private Integer subjectId;
	@Column(name = "subject_name")
	private String subjectName;
	public Integer getSubjectId() {
	  return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
	  this.subjectId = subjectId;
	}
	public String getSubjectName() {
	  return subjectName;
	}
	public void setSubjectName(String subjectName) {
	  this.subjectName = subjectName;
	}
	
}
