package so.sao.shop.dada.service;

import org.springframework.web.client.RestTemplate;
import so.sao.shop.dada.config.DadaProperties;
import so.sao.shop.dada.request.DadaBaseRequest;
import so.sao.shop.dada.response.DadaBaseResponse;
import so.sao.shop.dada.util.DadaUtils;

public class DadaService {

    private DadaProperties dadaProperties;

    private RestTemplate dadaRestTemplate;

    /**
     * 达达api版本
     * */
    private static String v="1.0";

    /**
     * 请求格式
     * */
    private static String format="json";

    public DadaBaseResponse addDadaOrder(DadaBaseRequest request){

        DadaBaseResponse response=executeRequest("/api/order/addOrder", request);
        return response;

    }

    private DadaBaseResponse executeRequest(String url, DadaBaseRequest request) {
        setBaseRequestParams(request);
        //return null;
        return dadaRestTemplate.postForObject(url, request, DadaBaseResponse.class);
    }

    private void setBaseRequestParams(DadaBaseRequest request){
        request.setAppKey(dadaProperties.getAppKey());
        request.setSourceId(dadaProperties.getSourceId());
        Long s=System.currentTimeMillis();
        request.setTimestamp(s.toString());
        request.setV(v);
        request.setFormat(format);
        request.setSignature(DadaUtils.getSign(request,dadaProperties.getAppSecret()));
    }

    public void setDadaProperties(DadaProperties dadaProperties) {
        this.dadaProperties = dadaProperties;
    }

    public void setDadaRestTemplate(RestTemplate dadaRestTemplate) {
        this.dadaRestTemplate = dadaRestTemplate;
    }
}
