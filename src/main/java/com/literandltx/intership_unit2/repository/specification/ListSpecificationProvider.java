package com.literandltx.intership_unit2.repository.specification;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public interface ListSpecificationProvider<T> {
    Specification<T> getSpecification(
            final String fieldName,
            final List<String> params
    );
}
