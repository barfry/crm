package com.coderslab.crm.specification;

import com.coderslab.crm.model.Department;
import com.coderslab.crm.model.Role;
import com.coderslab.crm.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Set;

public class UserSpecification implements Specification<User> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Department.class) {
                return builder.like(
                        root.<String>get("department").get("name"), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Set.class) {
                query.distinct(true);
                Join<User, Role> userRoleJoin = root.join("roles", JoinType.LEFT);
                return builder.like(
                        userRoleJoin.<String>get("name"), "%" + criteria.getValue() + "%");
            }
        }
            else {
                    return builder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
        return null;
    }

    public UserSpecification() {
    }

    public UserSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }
}