package com.ishopee.logisticsinventorymanagement.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "po_tab")
@Data
public class PurchaseOrder {

    @Id
    @GeneratedValue(generator = "po_gen")
    @SequenceGenerator(name = "po_gen", sequenceName = "po_seq")
    @Column(name = "po_id_col")
    private Integer id;
    @Column(name = "po_code_col")
    private String orderCode;
    @Column(name = "po_ref_col")
    private String refNumber;
    @Column(name = "po_qlty_col")
    private String qltyCheck;
    @Column(name = "po_status_col")
    private String status;
    @Column(name = "po_desc_col")
    private String desc;
    @ManyToOne
    @JoinColumn(name = "st_id_fk_col")
    private ShipmentType st;
    @ManyToOne
    @JoinColumn(name = "prod_vendor_id_fk_col")
    private ProductUserType vendor;
}
