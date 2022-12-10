package com.ishopee.logisticsinventorymanagement.repositories;

import com.ishopee.logisticsinventorymanagement.models.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepo extends JpaRepository<Visitor,Integer> {
}
