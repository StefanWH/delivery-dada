package so.sao.shop.dada.request;

import java.math.BigDecimal;

public class DadaQueryDeliveryFeeRequest extends DadaAddOrderRequest{

    private Integer isPrepay;

    public Integer getIsPrepay() {
        return isPrepay;
    }

    public void setIsPrepay(Integer isPrepay) {
        this.isPrepay = isPrepay;
    }

}

