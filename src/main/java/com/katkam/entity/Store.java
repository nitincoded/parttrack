package com.katkam.entity;

import javax.persistence.*;

/**
 * Created by Developer on 1/17/17.
 */
@Entity
@Table
public class Store {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true)
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


    public Store() {}
    public Store(int a_id, String a_name) { setId(a_id); setName(a_name); }
}
