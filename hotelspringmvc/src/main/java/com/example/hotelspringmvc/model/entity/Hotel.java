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
@Table(name="hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;

    @Column(name="name",length = 45)
    private String name;

    @Column(name="name_furigana",length = 45)
    private String namefurigana;

    @Column(name="url",length = 45)
    private String url;

    @Column(name="trade_name",length = 45)
    private String tradename;

    @Column(name="type",length = 45)
    private String type;

    @Column(name="phone_number",length = 45)
    private String phonenumber;

    @Column(name="fax_number",length = 45)
    private String faxnumber;

    @Column(name="pref_code",length = 45)
    private String prefcode;

    @Column(name="postal_code",length = 45)
    private String postalcode;

    @Column(name="address",length = 45)
    private String address;

    @Column(name="building_name",length = 45)
    private String buildingname;

    @Column(name="license_number",length = 45)
    private String licensenumber;

    @Column(name="image",length = 200)
    private String image;

    @Column(name="payment_method",length = 45)
    private String paymentmethod;

    @Column(name="create_at")
    private Date createat;

    @Column(name="update_at")
    private Date updateat;
}
