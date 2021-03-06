package com.example.agile.realstate.realstate.usecase;

import com.example.agile.realstate.realstate.common.Message;
import com.example.agile.realstate.realstate.domain.Photograph;
import com.example.agile.realstate.realstate.domain.Property;
import com.example.agile.realstate.realstate.dto.PropertyDto;
import com.example.agile.realstate.realstate.dto.response.PropertyDetailsResponse;
import com.example.agile.realstate.realstate.exception.BadRequestException;
import com.example.agile.realstate.realstate.mapper.PropertyMapper;
import com.example.agile.realstate.realstate.service.IPhotographService;
import com.example.agile.realstate.realstate.service.IPropertyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPropertyDetailsUseCase {
    private final Message message;

    private final IPropertyService propertyService;

    private final IPhotographService photographService;

    private final PropertyMapper propertyMapper;

    public GetPropertyDetailsUseCase(Message message, IPropertyService propertyService,
                                     IPhotographService photographService, PropertyMapper propertyMapper) {
        this.message = message;
        this.propertyService = propertyService;
        this.photographService = photographService;
        this.propertyMapper = propertyMapper;
    }

    public PropertyDetailsResponse execute(Long propertyId) {
        Property property = propertyService.findById(propertyId);
        if (property == null) {
            throw new BadRequestException(message.getMessage("invalid.property.id"));
        }
        PropertyDto propertyDto = propertyMapper.propertyToPropertyDto(property);
        List<Photograph> photographs = photographService.getByPropertyId(propertyId);
        return new PropertyDetailsResponse(propertyDto, photographs);
    }
}
