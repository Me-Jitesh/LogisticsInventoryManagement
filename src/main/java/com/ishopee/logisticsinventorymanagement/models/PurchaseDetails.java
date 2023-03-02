package com.ishopee.logisticsinventorymanagement.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "pdtl_tab")
@Data
public class PurchaseDetails {
    @Id
    @GeneratedValue(generator = "pdtl_gen")
    @SequenceGenerator(name = "pdtl_gen", sequenceName = "pdtl_sql")
    private Integer id;
    @Column(name = "po_pdtl_id_col")
    private Integer qty;
    @ManyToOne
    @JoinColumn(name = "part_id_fk_col")
    private Part part;
    @ManyToOne
    @JoinColumn(name = "po_id_fk_col")
    private PurchaseOrder po;
}
