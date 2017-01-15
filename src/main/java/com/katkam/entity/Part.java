package com.katkam.entity;

//import com.sun.istack.internal.NotNull;

import javax.persistence.*;

/**
 * Created by Developer on 1/15/17.
 */
@Entity
@Table
public class Part {
    @Id
    @GeneratedValue
    private int id;

//    @NotNull
    @Column(nullable = false)
    private String name;

    @Column
    private int manufacturer_id;

    @Column
    private int uom_id;


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

    public int getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(int manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public int getUom_id() {
        return uom_id;
    }

    public void setUom_id(int uom_id) {
        this.uom_id = uom_id;
    }


    public Part() {}
    public Part(int a_id, String a_name, int a_manufacturer_id, int a_uom_id) {
        setId(a_id);
        setName(a_name);
        setManufacturer_id(a_manufacturer_id);
        setUom_id(a_uom_id);
    }

    @Override
    public String toString() {
        return String.format("%d - %s", getId(), getName());
    }
}
