package com.ishopee.logisticsinventorymanagement.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "dnp_dtl_tab")
@Data
public class DnpDtl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dtl_id_col")
    private Integer id;
    @Column(name = "dtl_code_col")
    private String partCode;
    @Column(name = "dtl_cost_col")
    private Double baseCost;
    @Column(name = "dtl_qty_col")
    private Integer qty;
    @Column(name = "dtl_status_col")
    private String status;
}
