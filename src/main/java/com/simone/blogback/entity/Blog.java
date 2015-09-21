package com.simone.blogback.entity;

import java.io.Serializable;

public class Blog implements Serializable {
	private String url;
	private String title;
	private String content;
	private String postTime;
	
	public Blog() {
	}
	
	public Blog(String url, String title, String content, String postTime) {
		this.url = url;
		this.title = title;
		this.content = content;
		this.postTime = postTime;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getPostTime() {
		return postTime;
	}
	
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}
	
}
