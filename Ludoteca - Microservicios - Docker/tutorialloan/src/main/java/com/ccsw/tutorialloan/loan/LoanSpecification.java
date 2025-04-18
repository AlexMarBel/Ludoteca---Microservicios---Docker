package com.ccsw.tutorialloan.loan;

import com.ccsw.tutorialloan.common.criteria.SearchCriteria;
import com.ccsw.tutorialloan.loan.model.Loan;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class LoanSpecification implements Specification<Loan> {
    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public LoanSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
            Path<String> path = getPath(root);
            if (path.getJavaType() == String.class) {
                return builder.like(path, "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(path, criteria.getValue());
            }
        } else if (criteria.getOperation().equalsIgnoreCase("lessThanOrEqualTo") && criteria.getValue() != null) {
            LocalDate date = (LocalDate) criteria.getValue();
            return builder.lessThanOrEqualTo(root.get("startDate"), date);
        } else if (criteria.getOperation().equalsIgnoreCase("greaterThanOrEqualTo") && criteria.getValue() != null) {
            LocalDate date = (LocalDate) criteria.getValue();
            return builder.greaterThanOrEqualTo(root.get("endDate"), date);
        }

        return null;
    }

    private Path<String> getPath(Root<Loan> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }
        return expression;
    }
}
