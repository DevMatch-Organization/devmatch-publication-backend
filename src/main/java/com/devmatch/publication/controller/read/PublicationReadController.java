package com.devmatch.publication.controller.read;

import com.devmatch.publication.controller.ResponseController;
import com.devmatch.publication.dto.PublicationDto;
import com.devmatch.publication.dto.ResponseDTO;
import com.devmatch.publication.exception.ApiError;
import com.devmatch.publication.service.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/publication")
@Tag(name = "Publication")
public class PublicationReadController extends ResponseController {

    @Autowired
    private PublicationService service;

    @Operation(summary = "${publication_read_getFindAll}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${response_description_200}"),
            @ApiResponse(responseCode = "500", description = "${response_description_500}",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @GetMapping
    public ResponseEntity<Flux<ResponseDTO<PublicationDto>>> getFindAll(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) Sort.Direction sortDirection
    ) {
        return returnFluxOk(
                service.findAll(page, size, sortField, sortDirection)
        );
    }

    @Operation(summary = "${publication_read_getFindById}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${response_description_200}"),
            @ApiResponse(responseCode = "404", description = "${response_description_404}",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "${response_description_500}",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Mono<ResponseDTO<PublicationDto>>> getFindById(@PathVariable @NotBlank String id) {
        return returnMonoOk(service.findById(id));
    }
}