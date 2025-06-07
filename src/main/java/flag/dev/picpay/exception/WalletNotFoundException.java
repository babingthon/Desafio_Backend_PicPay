package flag.dev.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class WalletNotFoundException extends PicpayException{
    
    private Long walletId;

    public WalletNotFoundException(Long walletId) {
        this.walletId = walletId;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Wallet Not Found");
        pb.setDetail("Wallet Not Found for id " + walletId);

        return pb;
    }
}
