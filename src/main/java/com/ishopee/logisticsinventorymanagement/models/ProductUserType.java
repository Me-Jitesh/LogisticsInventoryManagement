package com.ishopee.logisticsinventorymanagement.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "product_user_type_tab")
@Data
public class ProductUserType {
    @Id
    @GeneratedValue(generator = "prodId")
    @SequenceGenerator(name = "prodId", sequenceName = "prod_usr_seq")
    @Column(name = "prod_user_id_col")
    private Integer id;
    @Column(name = "prod_user_type_col")
    private String userType;
    @Column(name = "prod_user_code_col")
    private String userCode;
    @Column(name = "prod_user_for_col")
    private String userFor;
    @Column(name = "prod_user_email_col")
    private String userEmail;
    @Column(name = "prod_user_contact_col")
    private String userContact;
    @Column(name = "prod_user_idtupe_col")
    private String userIdType;
    @Column(name = "prod_user_ifother_col")
    private String ifOther;
    @Column(name = "prod_user_idnum_col")
    private String userIdNumber;
}
