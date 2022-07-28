package com.coderslab.crm.specification;

import com.coderslab.crm.model.Department;
import com.coderslab.crm.model.Privilege;
import com.coderslab.crm.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DepartmentSpecification implements Specification<Department> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            }
            else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }

    public DepartmentSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public DepartmentSpecification() {
    }
}
