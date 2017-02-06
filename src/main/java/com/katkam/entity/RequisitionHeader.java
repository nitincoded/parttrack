package com.katkam.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Developer on 1/21/17.
 */
@Entity
@Table
public class RequisitionHeader {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column
    private Date submittedDate;

    @Column
    private String refno;

    @Column
    private String deliverTo;

    @OneToMany
    @JoinColumn(name = "header_id")
    private Set<RequisitionLine> lines;

    public Set<RequisitionLine> getLines() {
        return lines;
    }

    public void setLines(Set<RequisitionLine> lines) {
        this.lines = lines;
    }

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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getDeliverTo() {
        return deliverTo;
    }

    public void setDeliverTo(String deliverTo) {
        this.deliverTo = deliverTo;
    }
}
