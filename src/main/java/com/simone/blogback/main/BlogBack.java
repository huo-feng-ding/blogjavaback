package com.simone.blogback.main;

import java.io.IOException;

import com.simone.blogback.LoadBlog;
import com.simone.blogback.LoadLinks;

public class BlogBack {
	
	public static void main(String[] args) throws IOException {
		new LoadLinks().loadLinks();
		new LoadBlog().loadBlogs();
	}
	
}
