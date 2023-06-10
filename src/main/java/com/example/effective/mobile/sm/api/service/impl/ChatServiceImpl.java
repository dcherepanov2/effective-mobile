package com.example.effective.mobile.sm.api.service.impl;

import com.example.effective.mobile.sm.api.data.Chat;
import com.example.effective.mobile.sm.api.data.Message;
import com.example.effective.mobile.sm.api.repo.ChatRepo;
import com.example.effective.mobile.sm.api.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("chatServiceImpl")
public class ChatServiceImpl implements ChatService {

    private final ChatRepo chatRepo;

    @Autowired
    public ChatServiceImpl(ChatRepo chatRepo) {
        this.chatRepo = chatRepo;
    }

    @Override
    public Chat createChat() {
        Chat chat = new Chat();
        List<Message> messages = new ArrayList<>();
        chat.setMessages(messages);
        return chatRepo.save(chat);
    }

    // далее будет реализация добавления сообщения но пока что она не требуется
    // так как по тех заданию чат реализовывать не нужно
}
