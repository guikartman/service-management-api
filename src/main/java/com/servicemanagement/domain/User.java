package com.servicemanagement.domain;

import lombok.*;

import javax.persistence.*;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;
    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;
    @Column(name = "PASSWORD")
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
