package com.lbt.icon.demanddraft.config;

import com.lbt.icon.demanddraft.DemandDraftProductApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author foobar
 * @since 0.0.1
 */
@RequiredArgsConstructor
@EnableSwagger2
@Configuration
public class DemandDraftProductSwaggerConfig {

    private final DDProductServiceConfig ddProductServiceConfig;
    @Bean
    public Docket demandDraftProductApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("demanddraft")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lbt.icon.demanddraft"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(buildApiInfo())
                .securitySchemes(Collections.singletonList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext()))
                .pathMapping("/");

    }

  

    private List<AuthorizationScope> getScopes() {

        final List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        authorizationScopeList.add(new AuthorizationScope("webclient", "read all"));
        authorizationScopeList.add(new AuthorizationScope("mobileclient", "trust all"));

        return authorizationScopeList;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/**"))
                .build();
    }


    private OAuth securitySchema() {

        List<GrantType> grantTypes = new ArrayList<>();
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(ddProductServiceConfig.getAuthServerLink() + "/oauth/token");

        grantTypes.add(grantType);

        return new OAuth("Oauth2 Schema", getScopes(), grantTypes);

    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[getScopes().size()];
        return Collections.singletonList(
                new SecurityReference(
                        "Oauth2 Schema",
                        getScopes().toArray(authorizationScopes)
                )
        );
    }

    

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("Demand draft project API")
                .description("This API gives access to the Demand draft project Backend.")
                .version("0.0.1")
                .termsOfServiceUrl("https://www.longbridgetech.com/terms")
                .contact(
                        new Contact(
                                "Longbridge Tech Ltd",
                                "https://www.longbridgetech.com",
                                "foobar(at)longbridgetech.com"
                        )
                )
                .license("Apache 2.0")
                .licenseUrl("https://www.longbridgetech.com/license")
                .build();
    }
}

