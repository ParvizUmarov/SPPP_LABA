package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@Table(name = "services")
public class Services {

    @Id
    @Column( "id")
    private Integer id;

    @Column("name")
    private String name;

    @Column("price")
    private int price;
}
