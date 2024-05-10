package com.literandltx.intership_unit2.service;

import com.literandltx.intership_unit2.dto.label.CreateLabelRequest;
import com.literandltx.intership_unit2.dto.label.LabelResponse;
import com.literandltx.intership_unit2.dto.label.UpdateLabelRequest;
import com.literandltx.intership_unit2.mapper.LabelMapper;
import com.literandltx.intership_unit2.model.CartItem;
import com.literandltx.intership_unit2.model.Label;
import com.literandltx.intership_unit2.repository.LabelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LabelServiceV1 implements LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Override
    public LabelResponse save(
            final CreateLabelRequest request
    ) {
        final Label model = labelMapper.toModel(request);
        final CartItem cartItem = new CartItem();
        cartItem.setId(request.getCartItemId());
        model.setCartItem(cartItem);

        return labelMapper.toDto(labelRepository.save(model));
    }

    @Override
    public List<LabelResponse> findAll() {
        return labelRepository.findAll().stream()
                .map(labelMapper::toDto)
                .toList();
    }

    @Override
    public LabelResponse updateById(
            final UpdateLabelRequest request,
            final Long id
    ) {
        if (!labelRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot find label by id: "+ id);
        }
        final Label model = labelMapper.toModel(request);
        model.setId(id);

        return labelMapper.toDto(labelRepository.save(model));
    }

    @Override
    public ResponseEntity<Void> deleteById(
            final Long id
    ) {
        if (!labelRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot find label by id: " + id);
        }

        labelRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
