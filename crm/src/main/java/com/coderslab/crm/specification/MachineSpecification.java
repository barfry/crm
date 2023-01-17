package com.coderslab.crm.specification;

import com.coderslab.crm.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class MachineSpecification implements Specification<Machine> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<Machine> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            }
            else if(root.get(criteria.getKey()).getJavaType() == Integer.class){
                return builder.like(
                        root.get((criteria.getKey())).as(String.class), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Customer.class) {
                return builder.like(
                        root.<String>get("customer").get("name"), "%" + criteria.getValue() + "%");
            }
            else if (root.get(criteria.getKey()).getJavaType() == Type.class) {
                return builder.like(
                        root.<String>get("type").get("name"), "%" + criteria.getValue() + "%");
            }

        }
        else {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
        return null;
    }

    public MachineSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public MachineSpecification() {
    }
}
