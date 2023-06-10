package com.example.effective.mobile.sm.api.dto.response;

import com.example.effective.mobile.sm.api.data.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private String content;

    private LocalDateTime localDateTime;

    private String toContact;

    private String fromContact;

    public MessageDto(Message message) {
        this.content = message.getContent();
        this.fromContact = message.getFrom();
        this.localDateTime = message.getCreateDate();
        this.toContact = message.getTo();
    }
}
