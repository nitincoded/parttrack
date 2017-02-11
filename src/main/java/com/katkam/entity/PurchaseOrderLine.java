package com.katkam.entity;

import javax.persistence.*;

/**
 * Created by Developer on 1/28/17.
 */
@Table
@Entity
public class PurchaseOrderLine {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "header_id")
    private PurchaseOrderHeader header;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    @Column
    private double qty;

    @Column
    private double received_qty;

    @ManyToOne
    @JoinColumn(name = "requisition_line_id", nullable = true)
    private int requisitionLineId;


    public int getRequisitionLineId() {
        return requisitionLineId;
    }

    public void setRequisitionLineId(int requisitionLineId) {
        this.requisitionLineId = requisitionLineId;
    }

    public double getReceived_qty() {
        return received_qty;
    }

    public void setReceived_qty(double received_qty) {
        this.received_qty = received_qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PurchaseOrderHeader getHeader() {
        return header;
    }

    public void setHeader(PurchaseOrderHeader header) {
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
