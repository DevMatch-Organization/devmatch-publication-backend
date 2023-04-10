package com.devmatch.publication.dto;

import com.devmatch.publication.document.Publication;
import com.devmatch.publication.model.enums.TypePublicationEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PublicationDto {

    private String id;
    private String content;
    private TypePublicationEnum type;
    private String description;

    public PublicationDto(Publication publication) {
        this.id = publication.getId();
        this.content = publication.getContent();
        this.type = publication.getType();
        this.description = publication.getDescription();
    }

}