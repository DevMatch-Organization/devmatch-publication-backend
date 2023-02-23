package com.devmatch.publication.document;

import com.devmatch.publication.model.enums.TypePublicationEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Publication {

    @Id
    private String id;
    private String content;
    private TypePublicationEnum type;
    private String description;
}