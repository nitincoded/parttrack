package com.katkam.entity;

//import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Developer on 1/15/17.
 */
@Entity
@Table
public class Uom {
    @Id
    @GeneratedValue
    private int id;

//    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany
    private List<Part> parts;


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

    public List<Part> getParts() { return parts; }


    public Uom() {}
    public Uom(int a_id, String a_name) { setId(a_id); setName(a_name); }
}
