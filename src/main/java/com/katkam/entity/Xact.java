package com.katkam.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Developer on 1/17/17.
 */
@Entity
@Table
public class Xact {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    @Column(nullable = false)
    private double qty;

    @Column(nullable = false)
    private Date date;

    @Column
    private String narration;

    @Column
    private String refno;

    @Column
    private String typeCode;


    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }
}
