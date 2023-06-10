package com.example.effective.mobile.sm.api.repo;

import com.example.effective.mobile.sm.api.data.Friend;
import com.example.effective.mobile.sm.api.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("SELECT f FROM Friend f " +
            "WHERE (f.friend_id = :fr AND f.user = :fr1) OR (f.friend_id = :fr1 AND f.user = :fr)")
    Page<Friend> findByFriendIdOrUserId(@Param("fr") User fr, @Param("fr1") User fr1, Pageable pageable);

}
