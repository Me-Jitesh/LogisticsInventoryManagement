package com.ishopee.logisticsinventorymanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mus_tab")
public class Mus {
    @Id
    @GeneratedValue
    @Column(name = "mus_id_col")
    private Integer id;
    @Column(name = "mus_type_col")
    private String musType;
    @Column(name = "mus_model_col")
    private String musModel;
    @Column(name = "mus_desc_col")
    private String musDesc;
}
