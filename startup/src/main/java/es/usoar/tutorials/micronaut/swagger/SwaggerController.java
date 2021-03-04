package es.usoar.tutorials.micronaut.swagger;


import io.micronaut.core.io.ResourceResolver;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.server.types.files.StreamedFile;
import io.micronaut.validation.Validated;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Hidden
@Controller("/api")
@Validated
public class SwaggerController {
  Logger log = LoggerFactory.getLogger(SwaggerController.class);

  @Inject
  SwaggerConfig config;

  @Inject
  ResourceResolver resolver;

  @View("swagger/index")
  @Get
  public SwaggerConfig index() {
    log.info("Trying to render swagger-view");
    return config;
  }

  @View("swagger/index")
  @Get("/{url}")
  public SwaggerConfig renderSpec(@NotNull String url) {
    LoggerFactory.getLogger(SwaggerController.class).info("In URL");
    return new SwaggerConfig.Builder()
      .withDeepLinking(config.isDeepLinking())
      .withLayout(config.getLayout())
      .withVersion(config.getVersion())
      .withUrls(singletonList(new SwaggerConfig.URIConfig.Builder()
        .withName(url)
        .withURI(url)
        .build()))
      .build();
  }

  @Get("/api-docs/swagger/{path:.+}")
  public HttpResponse loadApiResource(HttpRequest request, String path) {
    String resourcePath = "classpath:META-INF/swagger/" + "account-server-0.1.yml";
    return buildResponseFromResource(resourcePath);
  }

  HttpResponse buildResponseFromResource(String resourcePath) {
    log.info("buildResponseFromResource");
    Optional<URL> resource = resolver.getResource(resourcePath);
    if (resource.isPresent()) {
      log.info("Hosting resource " + resourcePath);
      return HttpResponse.ok(new StreamedFile(resource.get()));
    }
    log.info("Resource not found!");
    return HttpResponse.notFound(resourcePath);
  }


}
