package com.literandltx.intership_unit2.mapper;

import com.literandltx.intership_unit2.config.MapperConfig;
import com.literandltx.intership_unit2.dto.group.GroupRequest;
import com.literandltx.intership_unit2.dto.group.GroupResponse;
import com.literandltx.intership_unit2.model.Group;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface GroupMapper {
    GroupResponse toDto(
            final Group model
    );

    Group toModel(
            final GroupRequest request
    );
}
