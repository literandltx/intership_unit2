package com.literandltx.intership_unit2.service;

import com.literandltx.intership_unit2.dto.group.GroupRequest;
import com.literandltx.intership_unit2.dto.group.GroupResponse;
import com.literandltx.intership_unit2.mapper.GroupMapper;
import com.literandltx.intership_unit2.model.Group;
import com.literandltx.intership_unit2.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GroupServiceV1 implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    public GroupResponse save(
            final GroupRequest request
    ) {
        final Group model = groupMapper.toModel(request);

        return groupMapper.toDto(groupRepository.save(model));
    }

    @Override
    public List<GroupResponse> findAll() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toDto)
                .toList();
    }

    @Override
    public GroupResponse updateById(
            final GroupRequest request,
            final Long id
    ) {
        if (!groupRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot find group by id: "+ id);
        }
        final Group model = groupMapper.toModel(request);
        model.setId(id);

        groupRepository.save(model);

        return null;
    }

    @Override
    public ResponseEntity<Void> deleteById(
            final Long id
    ) {
        if (!groupRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot find cartItem by id: " + id);
        }

        groupRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
