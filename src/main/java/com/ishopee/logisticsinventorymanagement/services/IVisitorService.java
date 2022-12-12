package com.ishopee.logisticsinventorymanagement.services;

import com.ishopee.logisticsinventorymanagement.models.Visitor;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IVisitorService {

    Visitor saveVisitorDetails(HttpServletRequest httpServletRequest);

    List<Visitor> getAllVisitors();

    List<Visitor> getRecent10Visitors();

    Long getVisitorsCount();

    void deleteAllVisitors();

    void deleteVisitor(Integer id);
}
