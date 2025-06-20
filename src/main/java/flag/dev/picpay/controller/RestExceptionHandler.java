package flag.dev.picpay.controller;

import flag.dev.picpay.exception.PicpayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({PicpayException.class})
    public ProblemDetail handlePicpayException(PicpayException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var fieldErrors = e.getFieldErrors()
                .stream()
                .map(f -> new InvalidParam(f.getField(), f.getDefaultMessage()))
                .toList();

        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Your request is invalid");
        pb.setProperty("filedErrors", fieldErrors);

        return pb;
    }

    private record InvalidParam(String name, String reason){

    }
}
