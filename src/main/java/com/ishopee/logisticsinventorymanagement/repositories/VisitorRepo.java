package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitorRepo extends JpaRepository<Visitor, Integer> {
    // Named Query To Get Recent 10 Visitors Details By Timestamp
    List<Visitor> findTop10ByOrderByLocaleTimestampDesc();
}
