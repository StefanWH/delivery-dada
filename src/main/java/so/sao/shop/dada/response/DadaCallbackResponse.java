package so.sao.shop.dada.response;

public class DadaCallbackResponse {

    /**
     * 达达订单号
     * */
    private String client_id;

    /**
     * 商户订单号
     * */
    private String order_id;

    /**
     * 达达订单状态
     * */
    private Integer order_status;

    /**
     * 取消原因
     * */
    private String cancel_reason;

    /**
     * 取消原因来源
     * */
    private Integer cancel_from;

    /**
     * 更新时间
     * */
    private int update_time;

    /**
     * 回调签名
     * */
    private String signature;

    /**
     * 达达配送员id(当配送员接单后会返回达达配送员相关信息)
     * */
    private Integer dm_id;

    /**
     * 达达配送员姓名(当配送员接单后会返回达达配送员相关信息)
     * */
    private String dm_name;

    /**
     * 达达配送员手机号(当配送员接单后会返回达达配送员相关信息)
     * */
    private String dm_mobile;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Integer getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Integer order_status) {
        this.order_status = order_status;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public Integer getCancel_from() {
        return cancel_from;
    }

    public void setCancel_from(Integer cancel_from) {
        this.cancel_from = cancel_from;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getDm_id() {
        return dm_id;
    }

    public void setDm_id(Integer dm_id) {
        this.dm_id = dm_id;
    }

    public String getDm_name() {
        return dm_name;
    }

    public void setDm_name(String dm_name) {
        this.dm_name = dm_name;
    }

    public String getDm_mobile() {
        return dm_mobile;
    }

    public void setDm_mobile(String dm_mobile) {
        this.dm_mobile = dm_mobile;
    }


}
