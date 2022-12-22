package com.ishopee.logisticsinventorymanagement.models;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "shipment_type_tab")
@Data
@XmlRootElement
public class ShipmentType {

    @Id
    @GeneratedValue(generator = "shipment_type_gen")
    @SequenceGenerator(name = "shipment_type_gen", sequenceName = "shipment_type_seq")
    @Column(name = "ship_id_col")
    private Integer id;
    @Column(name = "ship_mode_col")
    private String shipMode;
    @Column(name = "ship_code_col")
    private String shipCode;
    @Column(name = "enable_ship_col")
    private String enableShip;
    @Column(name = "ship_grad_col")
    private String shipGrad;
    @Column(name = "ship_desc_col")
    private String shipDesc;

}
