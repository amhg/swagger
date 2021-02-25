package es.usoar.tutorials.micronaut;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
  info = @Info(
    title = "swagger-server",
    version = "0.1",
    description = "My API",
    license = @License(name = "Apache 2.0", url = "https://foo.bar"),
    contact = @Contact(url = "https://gigantic-server.com", name = "Fred", email = "Fd@mail.com")
  )
)
public class Application {

  public static void main(String[] args) {
    Micronaut.run(Application.class);
  }
}
