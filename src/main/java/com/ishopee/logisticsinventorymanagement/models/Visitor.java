package com.ishopee.logisticsinventorymanagement.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "visitors_tab")
@Data
public class Visitor {

    @Id
    @GeneratedValue(generator = "visitors_gen")
    @SequenceGenerator(name = "visitors_gen", sequenceName = "visitors_seq")
    @Column(name = "visitors_id_col")
    private Integer id;
    @Column(name = "visitors_ip_col")
    private String ipAddress;
    @Embedded
    private VisitorLocation locale;
}
