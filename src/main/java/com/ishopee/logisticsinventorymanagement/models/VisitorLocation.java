package com.ishopee.logisticsinventorymanagement.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class VisitorLocation {
    @Column(name = "visitors_country_col")
    private String country;
    @Column(name = "visitors_cntry_code_col")
    private String countryCode;
    @Column(name = "visitors_state_col")
    private String state;
    @Column(name = "visitors_city_col")
    private String city;
    @Column(name = "visitors_zip_col")
    private String zip;
    @Column(name = "visitors_lati_col")
    private String latitude;
    @Column(name = "visitors_longi_col")
    private String longitude;
    @Column(name = "visitors_offset_col")
    private String utcOffset;
}
