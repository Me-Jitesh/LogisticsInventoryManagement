package com.ishopee.logisticsinventorymanagement.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sdtl_tab")
@Data
public class SaleDetails {
    @Id
    @GeneratedValue(generator = "sdtl_gen")
    @SequenceGenerator(name = "sdtl_gen", sequenceName = "sdtl_sql")
    private Integer id;
    @Column(name = "sdtl_qty_col")
    private Integer qty;
    @ManyToOne
    @JoinColumn(name = "part_id_fk_col")
    private Part part;
    @ManyToOne
    @JoinColumn(name = "po_id_fk_col")
    private PurchaseOrder po;
}
