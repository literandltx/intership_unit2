package com.literandltx.intership_unit2.mapper;

import com.literandltx.intership_unit2.config.MapperConfig;
import com.literandltx.intership_unit2.dto.label.CreateLabelRequest;
import com.literandltx.intership_unit2.dto.label.LabelResponse;
import com.literandltx.intership_unit2.dto.label.UpdateLabelRequest;
import com.literandltx.intership_unit2.model.Label;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface LabelMapper {
    LabelResponse toDto(
            final Label model
    );

    Label toModel(
            final CreateLabelRequest request
    );

    Label toModel(
            final UpdateLabelRequest request
    );
}
