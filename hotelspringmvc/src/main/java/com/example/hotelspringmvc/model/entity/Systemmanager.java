package com.example.hotelspringmvc.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="systemmanager")
public class Systemmanager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    @Column(name="email",length = 45)
    private String email;

    @Column(name="password",length=45)
    private String password;

    @Column(name="creat_at")
    private Date creatAt;

    @Column(name="update_at")
    private Date updateAt;
}
