package it.tndigit.iot.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableSwagger2
@ComponentScan("it.tndigit.iot.web.rest")
public class SwaggerConfig{

    @Bean
    public Docket apiInfoTNPat() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Versione 1")
                .apiInfo(apiInfoV1())
                .select()
                //.apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("it.tndigit.iot.web.rest"))
                .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }


    public ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("IO-TRENTINO")
                .description("API per la comunicazione con IO Italia")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version("0.8.8")
                .contact(new Contact("Trentino Digitale", "http://www.tndigit.it", "info@tndigit.it"))
                .build();
    }


    private Set< String > protocols() {
        Set< String > protocols = new HashSet<>();
        protocols.add("http");
        return protocols;
    }

    private List<? extends SecurityScheme> securitySchemes() {
        List<SecurityScheme> authorizationTypes =
                Arrays.asList(new ApiKey("token", "Authorization", "header"));
        return authorizationTypes;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts   = Arrays.asList(SecurityContext.builder().forPaths(PathSelectors.any()).securityReferences(securityReferences()).build());
        return securityContexts;
    }

    private List<SecurityReference> securityReferences() {
        List<SecurityReference> securityReferences = Arrays.asList(SecurityReference.builder().reference("token").scopes(new AuthorizationScope[0]).build());
        return securityReferences;
    }



}
