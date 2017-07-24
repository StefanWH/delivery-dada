package so.sao.shop.dada.request;

public class DadaBaseRequest implements DadaRequest {

    private String appKey;

    private String signature;

    private String timestamp;

    private String sourceId;

    private String body;

    private String v;

    private String format;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFormat() {
        return format;
    }

    public  void setFormat(String format) {
        this.format = format;
    }

    public  String getV() {
        return v;
    }

    public  void setV(String v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "DadaBaseRequest{" +
                "appKey='" + appKey + '\'' +
                ", signature='" + signature + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", body='" + body + '\'' +
                ", v='" + v + '\'' +
                ", format='" + format + '\'' +
                '}';
    }

}
