package com.literandltx.intership_unit2.repository.specification;

import com.literandltx.intership_unit2.dto.cartitem.search.NumberRangeRequest;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public interface SpecificationBuilder<T> {
    Specification<T> build(
            final List<String> titles,
            final List<String> description,
            final NumberRangeRequest rank,
            final List<Long> groupIds
    );
}

