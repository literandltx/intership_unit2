package com.literandltx.intership_unit2.controller;

import com.literandltx.intership_unit2.dto.label.CreateLabelRequest;
import com.literandltx.intership_unit2.dto.label.LabelResponse;
import com.literandltx.intership_unit2.dto.label.UpdateLabelRequest;
import com.literandltx.intership_unit2.service.LabelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/labels")
@RestController
public class LabelControllerV1 {
    private final LabelService labelServiceV1;

    @PostMapping
    public LabelResponse save(
            @RequestBody @Valid final CreateLabelRequest request
    ) {
        return labelServiceV1.save(request);
    }

    @GetMapping
    public List<LabelResponse> findAll() {
        return labelServiceV1.findAll();
    }

    @PutMapping
    public LabelResponse updateById(
            @RequestBody @Valid final UpdateLabelRequest request,
            @RequestParam(name = "id") final Long id
    ) {
        return labelServiceV1.updateById(request, id);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(
            @RequestParam(name = "id") final Long id
    ) {
        return labelServiceV1.deleteById(id);
    }
}
