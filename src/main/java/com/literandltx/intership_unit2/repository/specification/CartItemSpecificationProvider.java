package com.literandltx.intership_unit2.repository.specification;

import com.literandltx.intership_unit2.model.CartItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


@Component
public class CartItemSpecificationProvider implements ListSpecificationProvider<CartItem>, RangeSpecificationProvider<CartItem> {
    @Override
    public Specification<CartItem> getSpecification(
            final String fieldName,
            final List<String> params
    ) {
        return (root, query, criteriaBuilder) -> {
            final CriteriaBuilder.In<String> inPredicate = criteriaBuilder.in(root.get(fieldName));
            params.forEach(inPredicate::value);

            return criteriaBuilder.and(inPredicate);
        };
    }

    @Override
    public Specification<CartItem> getRangeSpecification(
            final String fieldName,
            final Double min,
            final Double max
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(fieldName), min, max);
    }
}
