package com.devmatch.publication.controller.write;

import com.devmatch.publication.dto.PublicationDto;
import com.devmatch.publication.form.PublicationPatchDescription;
import com.devmatch.publication.form.PublicationPost;
import com.devmatch.publication.service.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Publication")
public class PublicationWriteController {

    @Autowired
    private PublicationService service;

    @Operation(summary = "Criar uma Publicação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Operação inválida."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao realizar operação."),
    })
    @PostMapping(consumes = { MediaType.ALL_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PublicationDto> postCreate(
            @Valid @RequestPart("publication") Mono<PublicationPost> publicationPost,
            @RequestPart("content") Mono<FilePart> filePart
    ) {

        return service.create(publicationPost, filePart);
    }

    @Operation(summary = "Editar a descrição de uma Publicação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Operação inválida."),
            @ApiResponse(responseCode = "404", description = "O recurso não foi encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao realizar operação."),
    })
    @PatchMapping("{id}")
    public Mono<PublicationDto> patchDescription(
            @PathVariable("id") String id,
            @Valid @RequestBody Mono<PublicationPatchDescription> publicationPatchDescription
    ) {

        return service.updateDescription(id, publicationPatchDescription);
    }

    @Operation(summary = "Remover uma Publicação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operação realizada com sucesso, mas não retornou conteúdo."),
            @ApiResponse(responseCode = "400", description = "Operação inválida."),
            @ApiResponse(responseCode = "404", description = "O recurso não foi encontrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno ao realizar operação."),
    })
    @DeleteMapping("{id}")
    public Mono<Void> delete(
            @PathVariable("id") String id
    ) {

        return service.delete(id);
    }
}
