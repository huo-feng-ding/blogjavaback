package com.simone.blogback;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;

public class LoadBlogTest {
	
	@Test
	public void test() throws MalformedURLException, IOException {
		new LoadBlog().loadBlogs();
	}
}
