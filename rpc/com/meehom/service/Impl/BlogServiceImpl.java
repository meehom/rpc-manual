package com.meehom.service.Impl;

import com.meehom.common.Blog;
import com.meehom.service.BlogService;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 18:07
 */
public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("我的博客").userId(22).build();
        System.out.println("客户端查询了" + id + "博客");
        return blog;
    }
}
