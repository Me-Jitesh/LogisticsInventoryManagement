package com.ishopee.logisticsinventorymanagement.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "order_method_tab")
public class OrderMethod {

    @Id
    @GeneratedValue(generator = "om_gen")
    @SequenceGenerator(name = "om_gen", sequenceName = "om_sql_seq")
    @Column(name = "om_id_col")
    private Integer id;
    @Column(name = "om_mode_col")
    private String orderMode;
    @Column(name = "om_code_col")
    private String orderCode;
    @Column(name = "om_type_col")
    private String orderType;
    @Column(name = "om_note_col")
    private String orderNote;
    @ElementCollection
    @CollectionTable(name = "om_acpt_tab", joinColumns = @JoinColumn(name = "om_id_col"))
    private Set<String> orderAcpt;

}
