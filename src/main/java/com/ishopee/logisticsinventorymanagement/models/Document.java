package com.ishopee.logisticsinventorymanagement.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "doc_tab")
public class Document {
    @Id
    @Column(name = "doc_id_col")
    private Integer docId;
    @Column(name = "doc_name_col")
    private String docName;
    @Lob
    @Column(name = "doc_data_col")
    private byte[] docData;
}
