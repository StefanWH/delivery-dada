package so.sao.shop.dada;


import so.sao.shop.dada.errorcode.DadaErrorCode;

public class DadaException extends RuntimeException {

    public DadaException() {
    }

    public DadaException(int status, DadaErrorCode errorCode, String message) {

    }

    public DadaException(int status, int code, String message) {

    }

    public DadaException(String message) {
        super(message);
    }

    public DadaException(String message, Throwable cause) {
        super(message, cause);
    }

    public DadaException(Throwable cause) {
        super(cause);
    }

    public DadaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}