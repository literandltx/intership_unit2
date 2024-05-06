package com.literandltx.intership_unit2.service;

import com.literandltx.intership_unit2.dto.group.GroupRequest;
import com.literandltx.intership_unit2.dto.group.GroupResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface GroupService {
    GroupResponse save(
            final GroupRequest request
    );

    List<GroupResponse> findAll();

    GroupResponse updateById(
            final GroupRequest request,
            final Long id
    );

    ResponseEntity<Void> deleteById(
            final Long id
    );
}
