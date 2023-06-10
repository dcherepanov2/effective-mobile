package com.example.effective.mobile.sm.api.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    @Getter
    private Long chatId;

    @OneToMany(mappedBy = "chat")
    @Getter
    @Setter
    private List<Message> messages;
}
