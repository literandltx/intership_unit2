package com.literandltx.intership_unit2.service;

import com.literandltx.intership_unit2.dto.label.CreateLabelRequest;
import com.literandltx.intership_unit2.dto.label.LabelResponse;
import com.literandltx.intership_unit2.dto.label.UpdateLabelRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LabelService {
    LabelResponse save(
            final CreateLabelRequest request
    );

    List<LabelResponse> findAll();

    LabelResponse updateById(
            final UpdateLabelRequest request,
            final Long id
    );

    ResponseEntity<Void> deleteById(
            final Long id
    );
}
