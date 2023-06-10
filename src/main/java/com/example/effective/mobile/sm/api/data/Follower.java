package com.example.effective.mobile.sm.api.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "followers")
@Data
public class Follower {

    @EmbeddedId
    @Getter
    @Setter
    private FollowerId id;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    @Embeddable
    @Data
    public static class FollowerId implements Serializable {

        @Column(name = "follower_id")
        private Long followerId;

        @Column(name = "user_id")
        private Long userId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FollowerId that = (FollowerId) o;
            return Objects.equals(followerId, that.followerId) && Objects.equals(userId, that.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(followerId, userId);
        }
    }
}
