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
import java.net.URL;
import java.util.Optional;

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

  @Get("/account")
  public HttpResponse loadApiResource(HttpRequest request) {
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
