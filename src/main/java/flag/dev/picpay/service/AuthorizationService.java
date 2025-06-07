package flag.dev.picpay.service;

import flag.dev.picpay.client.AuthorizationClient;
import flag.dev.picpay.controller.dto.TransferDto;
import flag.dev.picpay.entity.Transfer;
import flag.dev.picpay.exception.PicpayException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(TransferDto transfer) {
        var resp = authorizationClient.isAuthorized();

        if (resp.getStatusCode().isError()) {
            throw new PicpayException();
        }

        return resp.getBody().authorized();
    }
}
