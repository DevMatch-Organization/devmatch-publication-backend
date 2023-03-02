package com.devmatch.publication.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(
        title = "Publication Devmatch Api",
        version = "1.0.0-SNAPSHOT",
        description = "Responsável sobre o ciclo de vida das Publicações",
//        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
        contact = @Contact(url = "https://github.com/DevMatch-Organization", name = "DevMatch Organization", email = "guhdalla011@gmail.com")
))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi myGroupedOpenApi() {
        String[] packagesToScan = { "com.devmatch.publication.controller" };
        return GroupedOpenApi.builder()
                .group("PublicationApi")
                .packagesToScan(packagesToScan)
                .build();
    }
}
