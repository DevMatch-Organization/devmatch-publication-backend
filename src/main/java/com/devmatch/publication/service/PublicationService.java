package com.devmatch.publication.service;

import com.devmatch.publication.document.Publication;
import com.devmatch.publication.dto.PublicationDto;
import com.devmatch.publication.form.PublicationPatchDescription;
import com.devmatch.publication.form.PublicationPost;
import org.springframework.data.domain.Sort;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PublicationService {

    Mono<PublicationDto> create(PublicationPost publicationPost, Mono<FilePart> filePart);
    Mono<PublicationDto> updateDescription(String id, PublicationPatchDescription publicationPatchDescription);
    Mono<PublicationDto> findById(String id);
    Flux<PublicationDto> findAll(Integer page, Integer size, String sortField, Sort.Direction sortDirection);
    Mono<Void> delete(String id);
}
