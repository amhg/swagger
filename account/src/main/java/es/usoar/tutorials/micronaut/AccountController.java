package es.usoar.tutorials.micronaut;

import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;


@Controller("/users")
public class AccountController {

  @Inject
  private ApplicationEventPublisher eventPublisher;

  @Operation(summary = "test",description = "Showcase")
  @ApiResponse(description = "Provides a simple test")
  @Get
  public List<Account> get() {
    var uuid = UUID.randomUUID();
    return List.of(new Account(uuid.toString(), "Account " + uuid));
  }

  @Post
  public void post(AccountData accountData) {
    var uuid = UUID.randomUUID();
    var account = new Account(uuid.toString(), accountData.getName());
    eventPublisher.publishEvent(new AccountCreatedEvent(account));
  }
}
