package com.coderslab.crm.specification;

import com.coderslab.crm.model.Customer;
import com.coderslab.crm.model.Manufacturer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ManufacturerSpecification implements Specification<Manufacturer> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<Manufacturer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

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

    public ManufacturerSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public ManufacturerSpecification() {
    }
}
