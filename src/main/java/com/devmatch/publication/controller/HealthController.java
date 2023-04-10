package com.devmatch.publication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publication/health")
@Tag(name = "Health")
public class HealthController {

    @GetMapping
    @Operation(summary = "${publication_health}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${response_description_200}"),
    })
    public String health(){
        return "OK";
    }
}
