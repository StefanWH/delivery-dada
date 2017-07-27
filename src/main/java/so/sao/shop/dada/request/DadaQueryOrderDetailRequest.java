package so.sao.shop.dada.request;

public class DadaQueryOrderDetailRequest extends DadaBaseRequest{

    /**
     * 订单编号
     * */
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
