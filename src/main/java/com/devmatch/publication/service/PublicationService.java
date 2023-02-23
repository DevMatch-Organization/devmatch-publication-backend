package com.devmatch.publication.service;

import com.devmatch.lib.annotation.log.Debug;
import com.devmatch.publication.document.Publication;
import com.devmatch.publication.dto.PublicationDto;
import com.devmatch.publication.form.PublicationPatchDescription;
import com.devmatch.publication.form.PublicationPost;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PublicationService {

    Mono<PublicationDto> create(Mono<PublicationPost> publicationPost, Mono<FilePart> filePart);
    Mono<PublicationDto> updateDescription(String id, Mono<PublicationPatchDescription> publicationPatchDescription);

    @Debug
    Mono<Publication> findById(String id);
    Flux<Publication> findAll();
    Mono<Void> delete(String id);
}
