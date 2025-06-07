package flag.dev.picpay.client;

import flag.dev.picpay.entity.Transfer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "notification",
        url = "${client.notification-service.url}"
)
public interface NotificationClient {
    @PostMapping
    ResponseEntity<Void> sendNotification(@RequestBody Transfer transfer);
}
