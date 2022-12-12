package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.models.Visitor;
import com.ishopee.logisticsinventorymanagement.repositories.VisitorRepo;
import com.ishopee.logisticsinventorymanagement.services.IVisitorService;
import com.ishopee.logisticsinventorymanagement.utilities.VisitorUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class VisitorServiceImpl implements IVisitorService {

    @Autowired
    private VisitorRepo repo;
    @Autowired
    private VisitorUtility utility;

    @Override
    public Visitor saveVisitorDetails(HttpServletRequest httpServletRequest) {
        Visitor visitor = utility.resolveVisitorDetails(httpServletRequest);
        return repo.save(visitor);
    }

    @Override
    public List<Visitor> getAllVisitors() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "locale.timestamp"));
    }

    @Override
    public List<Visitor> getRecent10Visitors() {
        return repo.findTop10ByOrderByLocaleTimestampDesc();
    }

    @Override
    public void deleteAllVisitors() {
        repo.deleteAll();
    }

    @Override
    public void deleteVisitor(Integer id) {
        repo.deleteById(id);
    }
}
