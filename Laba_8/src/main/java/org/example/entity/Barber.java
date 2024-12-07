package org.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "barber")
public class Barber {

    @Id
    @Column("id")
    private Integer id;

    @Column("name")
    private String name;

    @Column("surname")
    private String surname;

    @Column("birthday")
    private String birthday;

    @Column("phone")
    private String phone;

    @Column("mail")
    private String mail;

    @Column("password")
    private String password;

    @Column("auth_state")
    private Boolean authState;

    @Column("work_experience")
    private Integer workExperience;

    @Column("salon_id")
    private Integer salon;

    @Column("service_id")
    private Integer service;


}
