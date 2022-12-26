package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitorRepo extends JpaRepository<Visitor, Integer> {
    // Named Query To Get Recent 10 Visitors Details By Timestamp
    List<Visitor> findTop10ByOrderByLocaleTimestampDesc();

    // Chart
    @Query("SELECT visitor.locale.countryCode,count(visitor.locale.countryCode) from Visitor visitor group by visitor.locale.countryCode")
    List<Object[]> getVistorCountryCodeAndCount();

}
