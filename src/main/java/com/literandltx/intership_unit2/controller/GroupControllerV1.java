package com.literandltx.intership_unit2.controller;

import com.literandltx.intership_unit2.dto.group.GroupRequest;
import com.literandltx.intership_unit2.dto.group.GroupResponse;
import com.literandltx.intership_unit2.service.GroupService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/v1/groups")
@RestController
public class GroupControllerV1 {
    private final GroupService groupServiceV1;

    @PostMapping
    public GroupResponse save(
            @RequestBody @Valid final GroupRequest request
    ) {
        return groupServiceV1.save(request);
    }

    @GetMapping
    public List<GroupResponse> findAll() {
        return groupServiceV1.findAll();
    }

    @PutMapping
    public GroupResponse updateById(
            @RequestBody @Valid final GroupRequest request,
            @RequestParam(name = "id") final Long id
    ) {
        return groupServiceV1.updateById(request, id);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(
            @RequestParam(name = "id") final Long id
    ) {
        return groupServiceV1.deleteById(id);
    }
}
