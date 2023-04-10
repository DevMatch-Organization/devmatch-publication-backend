package com.devmatch.publication.model.mapper;

import com.devmatch.publication.document.Publication;
import com.devmatch.publication.dto.PublicationDto;
import org.springframework.stereotype.Component;

@Component
public class PublicationMapper {

    public static PublicationDto toDto(Object o) {
        Publication publication = (Publication) o;

        return PublicationDto
                .builder()
                .id(publication.getId())
                .content(publication.getContent())
                .type(publication.getType())
                .description(publication.getDescription())
                .build();
    }
}
