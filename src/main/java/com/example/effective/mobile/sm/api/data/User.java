package com.example.effective.mobile.sm.api.data;


import com.example.effective.mobile.sm.api.data.converts.UserStatusConverter;
import com.example.effective.mobile.sm.api.data.enums.USER_STATUS;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_contact_seq")
    @Column(name = "id")
    @SequenceGenerator(name = "user_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @Getter
    private Long id;

    @Column(name = "slug")
    @Getter
    @Setter
    private String slug;

    @Column(name = "create_date")
    @Getter
    private LocalDateTime createDate;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "password")
    @Setter
    private String password;


    @Column(name = "email")
    @Getter
    @Setter
    private String email;

    @Convert(converter = UserStatusConverter.class)
    @Column(name = "status")
    @Getter
    @Setter
    private USER_STATUS status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user2roles"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @NotNull
    @Getter
    @Setter
    private List<Role> roles;

    @OneToMany(fetch = FetchType.LAZY)
    @Getter
    @Setter
    @JoinTable(
            name = "user_contact"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<UserContact> userContacts;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Follower> followers;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map( x -> new SimpleGrantedAuthority(x.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status.equals(USER_STATUS.ENABLED);
    }

    @Override
    public boolean isAccountNonLocked()  {
        return status.equals(USER_STATUS.DISABLED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return status.equals(USER_STATUS.ENABLED);
    }

    @Override
    public String getPassword(){
        return password;
    }
}
