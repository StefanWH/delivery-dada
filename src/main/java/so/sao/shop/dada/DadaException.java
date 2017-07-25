package so.sao.shop.dada;

import org.springframework.http.HttpStatus;
import so.sao.shop.modules.errorcode.ErrorCode;
import so.sao.shop.modules.exceptions.ShopRuntimeException;

public class DadaException extends ShopRuntimeException {

    private static final long serialVersionUID = 1L;

    public DadaException(HttpStatus status, ErrorCode code, String message) {
        super(status, code, message);
    }

    public DadaException(HttpStatus status, int code, String message) {
        super(status, code, message);
    }
}