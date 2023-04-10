package com.devmatch.publication.controller;

import com.devmatch.publication.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class ResponseController {

    private <T> ResponseEntity<Mono<ResponseDTO<T>>> returnMonoResponse(Mono<T> response, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(response.map(ResponseDTO::new));
    }

    public <T> ResponseEntity<Mono<ResponseDTO<T>>> returnMonoOk(Mono<T> response) {
        return ResponseEntity
                .ok()
                .body(response.map(ResponseDTO::new));
    }

    public <T> ResponseEntity<Mono<ResponseDTO<T>>>  returnMonoOk() {
        return ResponseEntity
                .ok()
                .body(Mono.just(new ResponseDTO<>()));
    }

    public <T> ResponseEntity<Mono<ResponseDTO<T>>> returnMonoCreated(Mono<T> response) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response.map(ResponseDTO::new));
    }

    public <T> ResponseEntity<Mono<ResponseDTO<T>>>  returnMonoCreated() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Mono.just(new ResponseDTO<>()));
    }

    public <T> ResponseEntity<Mono<ResponseDTO<T>>> returnMonoNoContent() {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(Mono.just(new ResponseDTO<>()));
    }

    public <T> ResponseEntity<Mono<ResponseDTO<T>>>  returnMonoNotFound() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Mono.just(new ResponseDTO<>()));
    }

    private <T> ResponseEntity<Flux<ResponseDTO<T>>> returnFluxResponse(Flux<T> response, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(response.map(ResponseDTO::new));
    }

    public <T> ResponseEntity<Flux<ResponseDTO<T>>> returnFluxOk(Flux<T> response) {
        return ResponseEntity
                .ok()
                .body(response.map(ResponseDTO::new));
    }

    public <T> ResponseEntity<Flux<ResponseDTO<T>>> returnFluxOk() {
        return ResponseEntity
                .ok()
                .body(Flux.just(new ResponseDTO<>()));
    }

    public <T> ResponseEntity<Flux<ResponseDTO<T>>> returnFluxCreated(Flux<T> response) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response.map(ResponseDTO::new));
    }

    public <T> ResponseEntity<Flux<ResponseDTO<T>>> returnFluxCreated() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Flux.just(new ResponseDTO<>()));
    }

    public <T> ResponseEntity<Flux<ResponseDTO<T>>> returnFluxNoContent() {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(Flux.just(new ResponseDTO<>()));
    }

    public <T> ResponseEntity<Flux<ResponseDTO<T>>> returnFluxNotFound() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Flux.just(new ResponseDTO<>()));
    }

}