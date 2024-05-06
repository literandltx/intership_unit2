package com.literandltx.intership_unit2.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public interface RangeSpecificationProvider<T> {
    Specification<T> getRangeSpecification(
            final String fieldName,
            final Double min,
            final Double max
    );
}
