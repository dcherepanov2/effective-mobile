package com.example.effective.mobile.sm.api.repo;

import com.example.effective.mobile.sm.api.data.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepo extends JpaRepository<Chat, Long> {
    Chat findByChatId(Long id);
}
