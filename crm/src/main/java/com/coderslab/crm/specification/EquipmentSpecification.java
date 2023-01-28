package com.coderslab.crm.specification;

import com.coderslab.crm.model.Customer;
import com.coderslab.crm.model.Equipment;
import com.coderslab.crm.model.Machine;
import com.coderslab.crm.model.Type;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EquipmentSpecification implements Specification<Equipment> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<Equipment> root, CriteriaQuery<?> query, CriteriaBuilder builder) {


        if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            }
        }
        else {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
        return null;
    }

    public EquipmentSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public EquipmentSpecification() {
    }
}
