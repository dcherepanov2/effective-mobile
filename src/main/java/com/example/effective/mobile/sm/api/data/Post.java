package com.example.effective.mobile.sm.api.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "post_id_seq_gen")
    @SequenceGenerator(name = "post_id_seq_gen", sequenceName = "posts_id_seq", allocationSize = 1)
    @Getter
    private Long id;

    @Column(name = "slug")
    @Getter
    @Setter
    private String slug;


    @Column(name = "create_date")
    @Getter
    @Setter
    private LocalDateTime createDate;

    @Column(name = "title")
    @Getter
    @Setter
    private String title;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Column(name = "image")
    @Getter
    @Setter
    private String image;

    @OneToOne
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;
}
