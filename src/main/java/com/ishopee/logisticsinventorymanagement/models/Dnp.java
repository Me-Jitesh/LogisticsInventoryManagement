package com.ishopee.logisticsinventorymanagement.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "dnp_tab")
@Data
public class Dnp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dnp_id_col")
    private Integer id;
    @Column(name = "dnp_code_col")
    private String dnpCode;
    @Column(name = "dnp_type_col")
    private String dnpType;
    @Column(name = "dnp_desc_col")
    private String dnpDesc;
    @ManyToOne
    @JoinColumn(name = "dnp_poid_fk", unique = true)
    private PurchaseOrder po;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dnp_dtlid_fk")
    private Set<DnpDtl> dnpDtl;
}
