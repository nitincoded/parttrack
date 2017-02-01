package com.katkam.entity;

import javax.persistence.*;

/**
 * Created by Developer on 2/1/17.
 */
@Entity
@Table
public class Supplier {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
