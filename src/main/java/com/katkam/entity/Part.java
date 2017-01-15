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

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "uom_id")
    private Uom uom;


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

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer a_manufacturer) {
        this.manufacturer = a_manufacturer;
    }

    public Uom getUom() {
        return uom;
    }

    public void setUom(Uom a_uom) {
        this.uom = a_uom;
    }


    public Part() {}

    @Override
    public String toString() {
        return String.format("%d - %s", getId(), getName());
    }
}
