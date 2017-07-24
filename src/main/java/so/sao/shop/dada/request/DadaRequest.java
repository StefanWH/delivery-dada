package so.sao.shop.dada.request;

public interface DadaRequest {

    void setAppKey(String appKey);

    void setSignature(String signature);

    void setTimestamp(String timeStamp);

    void setBody(String body);

    void setSourceId(String sourceId);
}
