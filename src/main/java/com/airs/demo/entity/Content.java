package com.airs.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="content")
public class Content {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "content_id")
	private Integer contentId;
	@Column(name = "content_level")
	private Integer contentLevel;
	@Column(name = "content_address")
	private String contentAddress;
	


public Integer getContentId(){
	return contentId;
}
public void setContentId(Integer contentId) {
	  this.contentId = contentId;
	}
	public Integer getContentLevel() {
	  return contentLevel;
	}
	public void setContentLevel(Integer contentLevel) {
	  this.contentLevel = contentLevel;
	}
	public String getContentAddress() {
	  return contentAddress;
	}
	public void setContentAddress(String contentAddress) {
	  this.contentAddress = contentAddress;
	}
	}
