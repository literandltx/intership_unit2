package com.literandltx.intership_unit2.repository.specification;

import com.literandltx.intership_unit2.dto.cartitem.search.NumberRangeRequest;
import com.literandltx.intership_unit2.model.CartItem;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CartItemSpecificationBuilder implements SpecificationBuilder<CartItem> {
    private final CartItemSpecificationProvider specificationProvider;

    @Override
    public Specification<CartItem> build(
            final List<String> titles,
            final List<String> description,
            final NumberRangeRequest rank,
            final List<Long> groupIds
    ) {
        final List<Specification<CartItem>> specificationList = new ArrayList<>();

        if (titles != null && !titles.isEmpty()) {
            final Specification<CartItem> specification = specificationProvider.getSpecification("title", titles);

            specificationList.add(specification);
        }

        if (description != null && !description.isEmpty()) {
            final Specification<CartItem> specification = specificationProvider.getSpecification("description", description);

            specificationList.add(specification);
        }

        if (groupIds != null && !groupIds.isEmpty()) {
            final List<String> list = groupIds.stream()
                    .map(String::valueOf)
                    .toList();

            final Specification<CartItem> specification = specificationProvider.getSpecification("group_id", list);

            specificationList.add(specification);
        }

        if (rank != null && (rank.getMax() != null || rank.getMin() != null)) {
            double min = 0;
            double max = Integer.MAX_VALUE;

            if (rank.getMax() != null) {
                max = rank.getMax();
            }
            if (rank.getMin() != null) {
                min = rank.getMin();
            }

            final Specification<CartItem> specification = specificationProvider.getRangeSpecification("rank", min, max);
            specificationList.add(specification);

        }

        if (specificationList.isEmpty()) {
            return Specification.where(null);
        }

        return Specification.allOf(specificationList);
    }
}
