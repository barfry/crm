package com.coderslab.crm.specification;

import com.coderslab.crm.model.*;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.Set;

public class InquirySpecification implements Specification<Inquiry> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<Inquiry> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == LocalDate.class) {
                return builder.like(
                        root.<String>get("inquiryDate").as(String.class), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Customer.class) {
                return builder.like(
                        root.<String>get("customer").get("name"), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Machine.class) {
                return builder.like(
                        root.<String>get("machine").get("type").get("name"), "%" + criteria.getValue() + "%");
            }
        } else {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
        return null;
    }

    public InquirySpecification() {
    }

    public InquirySpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }
}
