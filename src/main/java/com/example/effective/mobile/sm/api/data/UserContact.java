package com.example.effective.mobile.sm.api.data;

import com.example.effective.mobile.sm.api.data.converts.ContactTypeConverter;
import com.example.effective.mobile.sm.api.data.enums.ContactType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Entity
@Table(name = "user_contact")
@ToString
public class UserContact {

    public UserContact() {
        this.codeTime = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_contact_seq")
    @SequenceGenerator(name = "user_contact_seq", sequenceName = "user_contact_id_seq", allocationSize = 1)
    @Getter
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    @JoinColumn(name = "user_id")
    private User userId;

    @Convert(converter = ContactTypeConverter.class)
    @Column(name = "type")
    @Getter
    @Setter
    private ContactType type;

    @Column(name = "approved")
    @Getter
    @Setter
    private Boolean approved;

    @Column(name = "code")
    @Getter
    @Setter
    private Integer code;

    @Column(name = "code_time")
    @Getter
    private LocalDateTime codeTime;

    @Column(name = "contact")
    @Getter
    @Setter
    private String contact;

}
