package com.blog.repositories;

import com.blog.entities.Category;
import com.blog.entities.Posts;
import com.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {

    List<Posts> findByUsers(User user);
    List<Posts> findByCategory(Category category);
    List<Posts> findByTitleContainingIgnoreCase(String title);
}
