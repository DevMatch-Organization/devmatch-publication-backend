package com.devmatch.publication.form;

import com.devmatch.publication.document.Publication;
import jakarta.validation.constraints.NotBlank;
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
public class PublicationPatchDescription {

    @NotBlank
    private String description;

    public Publication toDocument() {
        return Publication.builder()
                .description(description)
                .build();
    }
}
