package com.example.effective.mobile.sm.api.dto.response;

import com.example.effective.mobile.sm.api.data.Chat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;


public class ChatResponseDto {

    @JsonProperty
    @Getter
    private final List<MessageDto> messages;
    public ChatResponseDto(Chat chat) {
        messages = chat.getMessages()
                       .stream()
                       .map(MessageDto::new)
                       .collect(Collectors.toList());
    }
}
