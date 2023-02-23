package com.devmatch.publication.controller.read;

import com.devmatch.publication.document.Publication;
import com.devmatch.publication.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/publication")
public class PublicationReadController {

    @Autowired
    private PublicationService service;

    @GetMapping
    public Flux<Publication> getFindAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Publication> getFindById(@PathVariable String id) {
        return service.findById(id);
    }
}