package com.katkam.entity;

import javax.persistence.*;

/**
 * Created by Developer on 1/21/17.
 */
@Entity
@Table
public class PickticketLine {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "header_id")
    private PickticketHeader header;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    @Column
    private double qty;

    @Column
    private double issued_qty;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PickticketHeader getHeader() {
        return header;
    }

    public void setHeader(PickticketHeader header) {
        this.header = header;
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

    public double getIssued_qty() {
        return issued_qty;
    }

    public void setIssued_qty(double issued_qty) {
        this.issued_qty = issued_qty;
    }
}
