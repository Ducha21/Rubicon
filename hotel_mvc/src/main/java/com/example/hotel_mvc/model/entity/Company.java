package com.example.hotel_mvc.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "name_furigana", length = 45)
    private String nameFurigana;

    @Column(name = "phone_number", length = 45)
    private String phoneNumber;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "prefCode", length = 45)
    private String prefCode;

    @Column(name = "address", length = 45)
    private String address;

    @Column(name = "postal_code", length = 45)
    private String postalCode;

    @Column(name = "building_name", length = 45)
    private String buildingName;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAT;

}