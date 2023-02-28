package com.ishopee.logisticsinventorymanagement.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "so_tab")
@Data
public class SaleOrder {

    @Id
    @GeneratedValue(generator = "so_gen")
    @SequenceGenerator(name = "so_gen", sequenceName = "so_seq")
    @Column(name = "so_id_col")
    private Integer id;
    @Column(name = "so_code_col")
    private String saleCode;
    @Column(name = "so_ref_col")
    private String refNumber;
    @Column(name = "so_stck_mode_col")
    private String status;
    @Column(name = "so_stk_src_col")
    private String stockMode;
    @Column(name = "so_status_col")
    private String stockSource;
    @Column(name = "so_desc_col")
    private String desc;
    @ManyToOne
    @JoinColumn(name = "st_id_fk_col")
    private ShipmentType st;
    @ManyToOne
    @JoinColumn(name = "prod_cstmr_id_fk_col")
    private ProductUserType customer;
}
