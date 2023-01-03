package com.ishopee.logisticsinventorymanagement.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "part_tab")
@Data
public class Part {
    @Id
    @GeneratedValue(generator = "part_gen")
    @SequenceGenerator(name = "part_gen", sequenceName = "part_seq")
    @Column(name = "part_id_col")
    private Integer id;
    @Column(name = "part_code_col")
    private String partCode;
    @Column(name = "part_cost_col")
    private Double partCost;
    @Column(name = "part_curr_col")
    private String partCurrency;
    @Column(name = "part_width_col")
    private Double partWidth;
    @Column(name = "part_height_col")
    private Double partHeight;
    @Column(name = "part_len_col")
    private Double partLegnth;
    @Column(name = "part_desc_col")
    private String partDesc;
}
