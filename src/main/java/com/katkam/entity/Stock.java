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
    private Date date;

    @Column(nullable = false)
    private double qty;
}
