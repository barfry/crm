package com.coderslab.crm.specification;

import com.coderslab.crm.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

public class InterventionSpecification implements Specification<Intervention> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<Intervention> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == LocalDate.class) {
                return builder.like(
                        root.as(String.class), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == User.class) {
                return builder.like(
                        root.<String>get("technician").get("nickname"), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Inquiry.class) {
                return builder.like(
                        root.<String>get("inquiry").get("id"), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Long.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()).as(String.class), "%" + criteria.getValue() + "%");
            }
        } else {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
        return null;
    }

    public InterventionSpecification() {
    }

    public InterventionSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }
}

