package com.devmatch.publication.repository;

import com.devmatch.publication.document.Publication;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PublicationRepository extends ReactiveMongoRepository<Publication, String> {
}