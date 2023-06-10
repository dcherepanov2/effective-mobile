package com.example.effective.mobile.sm.api.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "friends")
@ToString
public class Friend {
    @EmbeddedId
    @Getter
    @Setter
    private FriendId id;

    @ManyToOne
    @MapsId("friendId")
    @JoinColumn(name = "friend_id")
    @Getter
    @Setter
    private User friend_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;

    @Embeddable
    @Data
    public static class FriendId implements Serializable {

        @Column(name = "follower_id")
        private Long friendId;

        @Column(name = "user_id")
        private Long userId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FriendId that = (FriendId) o;
            return Objects.equals(friendId, that.friendId) && Objects.equals(userId, that.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(friendId, userId);
        }
    }
}
