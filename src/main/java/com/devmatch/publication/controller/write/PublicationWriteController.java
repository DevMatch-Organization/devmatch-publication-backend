package com.devmatch.publication.controller.write;

import com.devmatch.publication.controller.ResponseController;
import com.devmatch.publication.dto.PublicationDto;
import com.devmatch.publication.dto.ResponseDTO;
import com.devmatch.publication.exception.ApiError;
import com.devmatch.publication.form.PublicationPatchDescription;
import com.devmatch.publication.form.PublicationPost;
import com.devmatch.publication.service.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/publication")
@Slf4j
@Tag(name = "Publication")
public class PublicationWriteController extends ResponseController {

    @Autowired
    private PublicationService service;

    @Operation(summary = "${publication_write_postCreate}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "${response_description_201}"),
            @ApiResponse(responseCode = "400", description = "${response_description_400}",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "${response_description_500}",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @PostMapping(consumes = { MediaType.ALL_VALUE })
    public ResponseEntity<Mono<ResponseDTO<PublicationDto>>> postCreate(
            @RequestPart("publication") @Valid PublicationPost publicationPost,
            @RequestPart("content") Mono<FilePart> filePart
    ) {
        return returnMonoCreated(service.create(publicationPost, filePart));
    }

    @Operation(summary = "${publication_write_patchDescription}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${response_description_200}"),
            @ApiResponse(responseCode = "400", description = "${response_description_400}",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "${response_description_404}",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "${response_description_500}",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @PatchMapping("{id}")
    public ResponseEntity<Mono<ResponseDTO<PublicationDto>>> patchDescription(
            @PathVariable("id") @Valid @NotBlank String id,
            @Valid @RequestBody PublicationPatchDescription publicationPatchDescription
    ) {

        return returnMonoOk(service.updateDescription(id, publicationPatchDescription));
    }

    @Operation(summary = "${publication_write_delete}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${response_description_200}"),
            @ApiResponse(responseCode = "400", description = "${response_description_400}",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "${response_description_404}",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "${response_description_500}",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Mono<ResponseDTO<Void>>> delete(
            @PathVariable("id") @Valid @NotBlank String id
    ) {

        return returnMonoOk(service.delete(id));
    }
}
