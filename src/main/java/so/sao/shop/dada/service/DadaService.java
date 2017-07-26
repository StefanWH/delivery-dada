package so.sao.shop.dada.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import so.sao.shop.dada.config.DadaProperties;
import so.sao.shop.dada.request.*;
import so.sao.shop.dada.response.DadaBaseResponse;
import so.sao.shop.dada.util.DadaUtils;
import so.sao.shop.dada.util.JsonMapper;


import java.util.Map;

public class DadaService {

    private DadaProperties dadaProperties;

    private RestTemplate dadaRestTemplate;

    private JsonMapper jsonMapper;


    /**
     * 达达api版本
     * */
    private static String v="1.0";

    /**
     * 请求格式
     * */
    private static String format="json";

    /**
     * 新增订单达达接口
     * */
    public DadaBaseResponse addDadaOrder(DadaAddOrderRequest dadaAddOrderRequest){

        //先将DadaAddOrderRequest中驼峰形式的属性转化为下划线形式。
        Map<String , Object> addRequestMap = DadaUtils.entityTransToMap(dadaAddOrderRequest);
        //设置通用请求的body
        DadaBaseRequest dadaRequest = new DadaBaseRequest();
        dadaRequest.setBody(jsonMapper.toJson(addRequestMap));

        DadaBaseResponse response=executeRequest("/api/order/addOrder", dadaRequest);
        return response;

    }

    /**
     * 查询订单运费接口（实际为查询第三方（达达）的订单号）
     * */
    public DadaBaseResponse queryThirdPartyNo(DadaQueryDeliveryFeeRequest request){

        DadaBaseRequest dadaRequest = setBaseBody(request);
        DadaBaseResponse response=executeRequest("/api/order/queryDeliverFee", dadaRequest);
        return response;
    }

    /**
     * 订单发布接口
     * */
    public DadaBaseResponse publishOrder(DadaPublishOrderRequest request){

        DadaBaseRequest dadaRequest = setBaseBody(request);
        DadaBaseResponse response=executeRequest("/api/order/addAfterQuery", dadaRequest);
        return response;
    }


    /**
     * 查询城市信息接口
     * */
    public DadaBaseResponse queryCityDetail(DadaBaseRequest request){
        request.setBody("");
        DadaBaseResponse response=executeRequest("/api/cityCode/list", request);
        return response;
    }

    /**
     * 查询订单取消原因接口
     * */
    public DadaBaseResponse queryCancelReason(DadaBaseRequest request){
        request.setBody("");
        DadaBaseResponse response=executeRequest("/api/order/cancel/reasons", request);
        return response;
    }

    private DadaBaseResponse executeRequest(String url, DadaBaseRequest request) {
        setBaseRequestParams(request);
        //将request中的驼峰形式的属性转化为"_"形式的map
        Map<String , Object> dadaRequestMap = DadaUtils.entityTransToMap(request);
        return dadaRestTemplate.postForObject(url, dadaRequestMap, DadaBaseResponse.class);
    }

    private void setBaseRequestParams(DadaBaseRequest request){
        //request.setAppKey(dadaProperties.getAppKey());
        //request.setSourceId(dadaProperties.getSourceId());
        request.setAppKey("dada339f050d417cda9");
        request.setSourceId("73753");

        Long s=System.currentTimeMillis();
        request.setTimestamp(s.toString());
        request.setV(v);
        request.setFormat(format);
        //request.setSignature(DadaUtils.getSign(request,dadaProperties.getAppSecret()));
        request.setSignature(DadaUtils.getSign(request,"bf0f92e9d6f4002d4f48248eff55b793"));
    }

    private DadaBaseRequest setBaseBody(DadaBaseRequest request){
        //先将DadaAddOrderRequest中驼峰形式的属性转化为下划线形式。
        Map<String , Object> requestBodyMap = DadaUtils.entityTransToMap(request);
        //设置通用请求的body
        DadaBaseRequest dadaRequest = new DadaBaseRequest();
        dadaRequest.setBody(jsonMapper.toJson(requestBodyMap));

        return dadaRequest;
    }

    public void setDadaProperties(DadaProperties dadaProperties) {
        this.dadaProperties = dadaProperties;
    }

    public void setDadaRestTemplate(RestTemplate dadaRestTemplate) {
        this.dadaRestTemplate = dadaRestTemplate;
    }

    public void setJsonMapper(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

}
