package com.devmatch.publication.controller.write;

import com.devmatch.publication.dto.PublicationDto;
import com.devmatch.publication.form.PublicationPatchDescription;
import com.devmatch.publication.form.PublicationPost;
import com.devmatch.publication.service.PublicationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/publication")
@Slf4j
public class PublicationWriteController {

    @Autowired
    private PublicationService service;

    @PostMapping(consumes = { MediaType.ALL_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PublicationDto> postCreate(
            @Valid @RequestPart("publication") Mono<PublicationPost> publicationPost,
            @RequestPart("content") Mono<FilePart> filePart
    ) {

        return service.create(publicationPost, filePart);
    }

    @PatchMapping("{id}")
    public Mono<PublicationDto> patchDescription(
            @PathVariable("id") String id,
            @Valid @RequestBody Mono<PublicationPatchDescription> publicationPatchDescription
    ) {

        return service.updateDescription(id, publicationPatchDescription);
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(
            @PathVariable("id") String id
    ) {

        return service.delete(id);
    }
}
