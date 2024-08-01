package com.ford.ces.api.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ford.cloudnative.base.api.StandardErrorResponse;
import com.ford.cloudnative.base.app.web.swagger.SwaggerProperties;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.DefaultPathProvider;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.util.function.Predicate;

import static com.ford.ces.api.constant.SwaggerConstants.*;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
@EnableSwagger2
public class SwaggerConfig {

    SwaggerProperties properties;
    ServletContext servletContext;

    @Autowired
    SwaggerConfig(ServletContext servletContext, SwaggerProperties properties){
        this.servletContext = servletContext;
        this.properties = properties;
    }

    @Bean
    @ConditionalOnProperty(name="cn.app.swagger.configure-docket", havingValue="false")
    public Docket cesAPI() { return createDocket(); }

    public Docket createDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(securityContexts())
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(properties.isUseDefaultResponseMessages());

        String contextPath = servletContext.getContextPath();
        if (StringUtils.hasLength(contextPath)) {
            if (!properties.isBasePath()) properties.getApidoc().setBasePath(contextPath);
            docket.pathProvider(new DefaultPathProvider() {
                @Override
                public String getOperationPath(String operationPath) {
                    String path = super.getOperationPath(operationPath);
                    return path.startsWith(contextPath) ? path.substring(contextPath.length()) : path;
                }
            });
        }

        ApiSelectorBuilder selectors = docket.select();
        List<Predicate<RequestHandler>> handlerPredicates = new ArrayList<>();
        String[] scanPackages = String.join(";", properties.getScanPackages()).trim().split(";");  // handle ; for backwards compatibility
        Arrays.stream(scanPackages).filter(Objects::nonNull)
                .forEach(str->handlerPredicates.add(RequestHandlerSelectors.basePackage(str)));
        if (!handlerPredicates.isEmpty()) {
            selectors.apis(or(handlerPredicates));
        }
        selectors.build();

        return docket;
    }

    private ApiKey apiKey() {
        return new ApiKey(APIKEY_NAME, APIKEY_KEYNAME, APIKEY_HEADER);
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{new AuthorizationScope("defalut", "provides access to all endpoints")};
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .forPaths(PathSelectors.any())
                .securityReferences(Collections.singletonList(new SecurityReference(APIKEY_NAME, new AuthorizationScope[0])))
                .build());
    }

    ApiInfo apiInfo() {
        SwaggerProperties.Display display = properties.getDisplay();
        return new ApiInfoBuilder()
                .title(display.getTitle())
                .description(display.getDescription())
                .contact(valueOrNull(new Contact(display.getContactName(), display.getContactUrl(), display.getContactEmail())))
                .version(display.getVersion())
                .license(display.getLicense())
                .licenseUrl(display.getLicenseUrl())
                .termsOfServiceUrl(display.getTermsOfServiceUrl())
                .build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class OAuthErrorSwaggerDefinition {
        @JsonProperty("error")
        @Size(min =0 ,max = 36)
        @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
        private String error;
        @JsonProperty("error_description")
        @Size(min =0 ,max = 36)
        @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
        private String errorDescription;
    }

    Contact valueOrNull(Contact contact) {
        return contact.getName() == null && contact.getUrl() == null && contact.getEmail() == null ? null : contact;
    }

    static <T> Predicate<T> or(Collection<Predicate<T>> predicates) {
        return t -> predicates.stream().anyMatch(predicate -> predicate.test(t));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = BAD_REQUEST, response = StandardErrorResponse.class),
            @ApiResponse(code = 403, message = FORBIDDEN),
            @ApiResponse(code = 404, message = RESOURCE_NOT_FOUND, response = StandardErrorResponse.class),
            @ApiResponse(code = 406, message = "Resource Not Acceptable", response = OAuthErrorSwaggerDefinition.class),
            @ApiResponse(code = 415, message = "UnSupported media type", response = OAuthErrorSwaggerDefinition.class),
            @ApiResponse(code = 429, message = "Too Many Requests", response = OAuthErrorSwaggerDefinition.class),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR, response = StandardErrorResponse.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = OAuthErrorSwaggerDefinition.class),
            @ApiResponse(code = 0, message = ALL_ERRORS, response = StandardErrorResponse.class)})
    public @interface SystemWideResponses {
    }
}
