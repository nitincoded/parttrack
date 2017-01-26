package com.katkam.entity;

import javax.persistence.*;

/**
 * Created by Developer on 1/21/17.
 */
@Entity
@Table
public class RequisitionLine {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "header_id")
    private RequisitionHeader header;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    @Column
    private double qty;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RequisitionHeader getHeader() {
        return header;
    }

    public void setHeader(RequisitionHeader header) {
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
}
