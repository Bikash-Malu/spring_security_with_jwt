package com.security.Learning.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Role {
    @Id
    private String id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
