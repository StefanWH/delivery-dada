package so.sao.shop.dada.response;

public class DadaCallbackResponse {

    /**
     * 达达订单号
     * */
    private String clientId;

    /**
     * 商户订单号
     * */
    private String orderId;

    /**
     * 达达订单状态
     * */
    private Integer orderStatus;

    /**
     * 取消原因
     * */
    private String cancelReason;

    /**
     * 取消原因来源
     * */
    private Integer cancelFrom;

    /**
     * 更新时间
     * */
    private int updateTime;

    /**
     * 回调签名
     * */
    private String signature;

    /**
     * 达达配送员id(当配送员接单后会返回达达配送员相关信息)
     * */
    private Integer dmId;

    /**
     * 达达配送员姓名(当配送员接单后会返回达达配送员相关信息)
     * */
    private String dmName;

    /**
     * 达达配送员手机号(当配送员接单后会返回达达配送员相关信息)
     * */
    private String dmMobile;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Integer getCancelFrom() {
        return cancelFrom;
    }

    public void setCancelFrom(Integer cancelFrom) {
        this.cancelFrom = cancelFrom;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getDmId() {
        return dmId;
    }

    public void setDmId(Integer dmId) {
        this.dmId = dmId;
    }

    public String getDmName() {
        return dmName;
    }

    public void setDmName(String dmName) {
        this.dmName = dmName;
    }

    public String getDmMobile() {
        return dmMobile;
    }

    public void setDmMobile(String dmMobile) {
        this.dmMobile = dmMobile;
    }

}
