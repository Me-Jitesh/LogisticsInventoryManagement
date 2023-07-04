package com.ishopee.logisticsinventorymanagement.models;

import com.ishopee.logisticsinventorymanagement.constants.RoleType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "roles_tab")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id_col")
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_type_col")
    private RoleType role;
}
