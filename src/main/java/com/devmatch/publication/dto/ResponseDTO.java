package com.devmatch.publication.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ResponseDTO<T> {

    private String timestamp;
    private T result;
    private String traceId;

    public ResponseDTO(T resultado) {
        this.timestamp = LocalDateTime.now().toString();
        this.result = resultado;
        this.traceId = UUID.randomUUID().toString();
    }

    public ResponseDTO() {
        this.timestamp = LocalDateTime.now().toString();
        this.traceId = UUID.randomUUID().toString();
    }
}
