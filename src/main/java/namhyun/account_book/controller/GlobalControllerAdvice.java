package namhyun.account_book.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public void handelRuntimeException(RuntimeException e) {
        //TODO 예외처리 추가해야함
        e.printStackTrace();
    }
}
