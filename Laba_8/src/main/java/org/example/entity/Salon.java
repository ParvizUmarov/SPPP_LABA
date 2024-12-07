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
@Table(name = "salons")
public class Salon {

    @Id
    @Column("id")
    private Integer id;

    @Column("address")
    private String address;

    @Column("images")
    private String images;

    @Column("longitude")
    private String longitude;

    @Column("latitude")
    private String latitude;

}
