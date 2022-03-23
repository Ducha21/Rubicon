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
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Integer id;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "first_name_furigana", length = 45)
    private String firstNameFurigana;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "last_name_furigana", length = 45)
    private String lastNameFurigana;

    @Column(name = "postal_code", length = 45)
    private String postalCode;

    @Column(name = "country", length = 45)
    private String country;

    @Column(name = "pref_code", length = 45)
    private String prefCode;

    @Column(name = "address", length = 45)
    private String address;

    @Column(name = "building_name", length = 45)
    private String buildingName;

    @Column(name = "work_place", length = 45)
    private String workPlace;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "phone_number", length = 45)
    private String phoneNumber;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "gender", length = 45)
    private String gender;

    @Column(name = "family_phone", length = 45)
    private String familyPhone;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

}
