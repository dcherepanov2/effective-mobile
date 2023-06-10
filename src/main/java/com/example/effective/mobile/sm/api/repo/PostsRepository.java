package com.example.effective.mobile.sm.api.repo;

import com.example.effective.mobile.sm.api.data.Post;
import com.example.effective.mobile.sm.api.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PostsRepository extends JpaRepository<Post, Long> {
    Post findBySlug(String slug);

    @Query(value = "SELECT p1 FROM Post AS p1 INNER JOIN Follower AS f1 ON f1.user = p1.user WHERE f1.follower = :user")
    Page<Post> getNewPosts(@Param("user") User follower, Pageable page);
}
