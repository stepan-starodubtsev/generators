package com.example.generatorsdiplomawork.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    private Unit unit;
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    public User(String login, String password, Unit unit, UserRoles role) {
        this.login = login;
        this.password = password;
        this.unit = unit;
        this.role = role;
    }
}
