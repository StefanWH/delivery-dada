package so.sao.shop.dada;

import org.springframework.http.HttpStatus;


import so.sao.shop.dada.errorcode.DadaErrorCode;

public class DadaException {

    public DadaException() {
    }

    public DadaException(HttpStatus status, DadaErrorCode errorCode, String message) {

    }

    public DadaException(HttpStatus status, int code, String message) {

    }
}