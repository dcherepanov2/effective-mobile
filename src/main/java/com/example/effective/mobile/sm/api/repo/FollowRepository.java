package com.example.effective.mobile.sm.api.repo;

import com.example.effective.mobile.sm.api.data.Follower;
import com.example.effective.mobile.sm.api.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FollowRepository extends JpaRepository<Follower, Long> {
    List<Follower> findAllByUser(User follower);

    Follower findByFollowerAndUser(User follower, User user);

}
