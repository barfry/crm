package com.coderslab.crm.specification;

import com.coderslab.crm.model.Customer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;


public class CustomerSpecification implements Specification<Customer> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            }
            else if(root.get(criteria.getKey()).getJavaType() == Integer.class){
                return builder.like(
                        root.get((criteria.getKey())).as(String.class), "%" + criteria.getValue() + "%");
            }
        }
        else {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
        return null;
    }

    public CustomerSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public CustomerSpecification() {
    }
}
