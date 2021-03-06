package com.katkam.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Developer on 1/17/17.
 */
@Entity
@Table
public class Stock {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part; //uom is from part

    @Column(nullable = false)
    private double qty;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }
}
