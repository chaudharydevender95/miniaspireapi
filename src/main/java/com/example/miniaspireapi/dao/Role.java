package com.example.miniaspireapi.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * @author devenderchaudhary
 */
@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
