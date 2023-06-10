package com.example.effective.mobile.sm.api.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    @Getter
    @Setter
    private Chat chat;

    @Column(name = "content")
    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    @Column(name = "from_user")
    private String from;

    @Getter
    @Setter
    @Column(name = "to_user")
    private String to;

    @Column(name = "createDate")
    @Getter
    @Setter
    private LocalDateTime createDate;
}