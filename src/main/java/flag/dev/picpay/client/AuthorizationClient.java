package flag.dev.picpay.client;

import flag.dev.picpay.client.dto.AuthorizationResponde;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(
        name = "authorization",
        url = "${client.authorization-service.url}"
)
public interface AuthorizationClient {

    @GetMapping
    ResponseEntity<AuthorizationResponde> isAuthorized();
}
