package com.example.effective.mobile.sm.api.repo;

import com.example.effective.mobile.sm.api.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long> {
//    User findByEmail(String email);

    @Query(value = "FROM User AS u INNER JOIN FETCH UserContact AS uc ON uc.contact = :contact AND uc.userId.id = u.id")
    User findUserByContact(@Param("contact") String contact);

}
