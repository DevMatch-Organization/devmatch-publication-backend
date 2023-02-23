package com.devmatch.publication.form;

import com.devmatch.publication.document.Publication;
import com.devmatch.publication.model.enums.TypePublicationEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PublicationPost {

    @NotBlank
    private String description;
    @NotNull
    private TypePublicationEnum type;

    public Publication toDocument() {
        return Publication.builder()
                        .description(description)
                        .type(type)
                .build();
    }
}
