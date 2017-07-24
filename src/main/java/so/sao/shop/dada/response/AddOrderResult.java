package so.sao.shop.dada.response;

public class AddOrderResult implements Result {

    private Double distance;

    private Double fee;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

}
