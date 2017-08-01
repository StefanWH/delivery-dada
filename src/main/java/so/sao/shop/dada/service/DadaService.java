package so.sao.shop.dada.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.web.client.RestTemplate;
import so.sao.shop.dada.config.DadaProperties;
import so.sao.shop.dada.request.*;
import so.sao.shop.dada.response.DadaBaseResponse;
import so.sao.shop.dada.util.DadaUtils;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class DadaService {

    private DadaProperties dadaProperties;

    private RestTemplate dadaRestTemplate;

    private static ObjectMapper mapper = new ObjectMapper();

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
        dadaRequest.setBody(toJson(addRequestMap));

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
     * 取消订单接口
     * */
    public DadaBaseResponse cancelOrder(DadaCancelOrderRequest request){

        DadaBaseRequest dadaRequest = setBaseBody(request);
        DadaBaseResponse response=executeRequest("/api/order/formalCancel", dadaRequest);
        return response;
    }

    /**
     * 查询订单详情接口
     * */
    public DadaBaseResponse queryOrderDetail(DadaQueryOrderDetailRequest request){

        DadaBaseRequest dadaRequest = setBaseBody(request);
        DadaBaseResponse response=executeRequest("/api/order/status/query", dadaRequest);
        return response;

    }

    /**
     * 创建达达门店
     * */
    public DadaBaseResponse createDadaShop(DadaCreateShopRequest request){

        //将传来的请求转化为map形式
        Map<String , Object> requestBodyMap = DadaUtils.entityTransToMap(request);

        DadaBaseRequest dadaRequest = new DadaBaseRequest();
        ArrayList<Map<String , Object>> list = new ArrayList<Map<String , Object>>();
        list.add(requestBodyMap);
        dadaRequest.setBody(toJson(list));
        DadaBaseResponse response=executeRequest("/api/shop/add", dadaRequest);
        return response;
    }

    /**
     * 查询达达门店详情
     * */
    public DadaBaseResponse queryDadaShop(DadaQueryShopRequest request){
        DadaBaseRequest dadaRequest = setBaseBody(request);
        DadaBaseResponse response=executeRequest("/api/shop/detail", dadaRequest);
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
        request.setAppKey(dadaProperties.getAppKey());
        request.setSourceId(dadaProperties.getSourceId());
//        request.setAppKey("dada339f050d417cda9");
//        request.setSourceId("73753");

        Long s=System.currentTimeMillis();
        request.setTimestamp(s.toString());
        request.setV(v);
        request.setFormat(format);
        request.setSignature(DadaUtils.getSign(request,dadaProperties.getAppSecret()));
//        request.setSignature(DadaUtils.getSign(request,"bf0f92e9d6f4002d4f48248eff55b793"));
    }

    private DadaBaseRequest setBaseBody(DadaBaseRequest request){
        //先将DadaAddOrderRequest中驼峰形式的属性转化为下划线形式。
        Map<String , Object> requestBodyMap = DadaUtils.entityTransToMap(request);
        //设置通用请求的body
        DadaBaseRequest dadaRequest = new DadaBaseRequest();
        dadaRequest.setBody(toJson(requestBodyMap));

        return dadaRequest;
    }

    //将实体类转化为json
    public String toJson(Object object) {
        try {
            ObjectWriter writer = mapper.writerFor(object.getClass());
            return writer.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDadaProperties(DadaProperties dadaProperties) {
        this.dadaProperties = dadaProperties;
    }

    public void setDadaRestTemplate(RestTemplate dadaRestTemplate) {
        this.dadaRestTemplate = dadaRestTemplate;
    }


}
