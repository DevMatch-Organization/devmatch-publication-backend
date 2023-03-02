package com.devmatch.publication.controller.read;

import com.devmatch.publication.document.Publication;
import com.devmatch.publication.service.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/publication")
@Tag(name = "Publication")
public class PublicationReadController {

    @Autowired
    private PublicationService service;

    @Operation(summary = "Listar as Publicações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
            @ApiResponse(responseCode = "204", description = "Operação realizada com sucesso, mas não retornou conteúdo."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao realizar operação."),
    })
    @GetMapping
    public Flux<Publication> getFindAll() {
        return service.findAll();
    }

    @Operation(summary = "Consultar uma Publicação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "O recurso não foi encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao realizar operação."),
    })
    @GetMapping("/{id}")
    public Mono<Publication> getFindById(@PathVariable String id) {
        return service.findById(id);
    }
}