package flag.dev.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class WalletDataAlreadyExistsExeption extends PicpayException{

    private String detail;

    public WalletDataAlreadyExistsExeption(String detail) {
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Wallet Data Already Exists");
        pb.setDetail(detail);

        return pb;

    }
}
