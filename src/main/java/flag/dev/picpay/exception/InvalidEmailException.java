package flag.dev.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InvalidEmailException extends PicpayException{
    private final String detail;

    public InvalidEmailException(String detail) {
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Invalid Email");
        pb.setDetail(detail);

        return pb;
    }
}
