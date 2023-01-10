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
    @Column(name = "part_code_col", nullable = false, length = 20,unique = true)
    private String partCode;
    @Column(name = "part_cost_col", nullable = false, length = 15)
    private Double partCost;
    @Column(name = "part_curr_col", nullable = false, length = 6)
    private String partCurrency;
    @Column(name = "part_width_col", nullable = false, length = 10)
    private Double partWidth;
    @Column(name = "part_height_col", nullable = false, length = 10)
    private Double partHeight;
    @Column(name = "part_len_col", nullable = false, length = 10)
    private Double partLegnth;
    @Column(name = "part_desc_col", nullable = false, length = 100)
    private String partDesc;
    @ManyToOne
    @JoinColumn(name = "mus_id_fk_col")
    private Mus mus;
    @ManyToOne
    @JoinColumn(name = "om_id_fk_col")
    private OrderMethod om;
}
