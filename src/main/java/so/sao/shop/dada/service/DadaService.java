package so.sao.shop.dada.service;

import java.util.ArrayList;
import java.util.Map;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import so.sao.shop.dada.DadaException;
import so.sao.shop.dada.config.DadaProperties;
import so.sao.shop.dada.request.DadaAddOrderRequest;
import so.sao.shop.dada.request.DadaBaseRequest;
import so.sao.shop.dada.request.DadaCancelOrderRequest;
import so.sao.shop.dada.request.DadaCreateShopRequest;
import so.sao.shop.dada.request.DadaOrderStatusChangeTestRequest;
import so.sao.shop.dada.request.DadaPublishOrderRequest;
import so.sao.shop.dada.request.DadaQueryDeliveryFeeRequest;
import so.sao.shop.dada.request.DadaQueryOrderDetailRequest;
import so.sao.shop.dada.request.DadaQueryShopRequest;
import so.sao.shop.dada.response.DadaBaseResponse;
import so.sao.shop.dada.util.DadaUtils;

public class DadaService {

    private DadaProperties dadaProperties;

    private OkHttpClient httpClient;

    private static final Gson GSON = new Gson();

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    /**
     * 达达api版本
     * */
    private static final String DATA_API_VERSION = "1.0";

    /**
     * 请求格式
     * */
    private static final String FORMAT = "json";

    /**
     * 新增订单达达接口
     * */
    public DadaBaseResponse addDadaOrder(DadaAddOrderRequest dadaAddOrderRequest){

        //先将DadaAddOrderRequest中驼峰形式的属性转化为下划线形式。
        Map<String , Object> addRequestMap = DadaUtils.entityTransToMap(dadaAddOrderRequest);
        //设置通用请求的body
        DadaBaseRequest dadaRequest = new DadaBaseRequest();
        dadaRequest.setBody(toJson(addRequestMap));

        return executeRequest("/api/order/addOrder", dadaRequest);

    }

    /**
     * 查询订单运费接口（实际为查询第三方（达达）的订单号）
     * */
    public DadaBaseResponse queryThirdPartyNo(DadaQueryDeliveryFeeRequest request){

        DadaBaseRequest dadaRequest = setBaseBody(request);
        return executeRequest("/api/order/queryDeliverFee", dadaRequest);
    }

    /**
     * 订单发布接口
     * */
    public DadaBaseResponse publishOrder(DadaPublishOrderRequest request){

        DadaBaseRequest dadaRequest = setBaseBody(request);
        return executeRequest("/api/order/addAfterQuery", dadaRequest);
    }

    /**
     * 取消订单接口
     * */
    public DadaBaseResponse cancelOrder(DadaCancelOrderRequest request){

        DadaBaseRequest dadaRequest = setBaseBody(request);
        return executeRequest("/api/order/formalCancel", dadaRequest);
    }

    /**
     * 查询订单详情接口
     * */
    public DadaBaseResponse queryOrderDetail(DadaQueryOrderDetailRequest request){

        DadaBaseRequest dadaRequest = setBaseBody(request);
        return executeRequest("/api/order/status/query", dadaRequest);

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
        return executeRequest("/api/shop/add", dadaRequest);
    }

    /**
     * 查询达达门店详情
     * */
    public DadaBaseResponse queryDadaShop(DadaQueryShopRequest request){
        DadaBaseRequest dadaRequest = setBaseBody(request);
        return executeRequest("/api/shop/detail", dadaRequest);
    }

    /**
     * 查询城市信息接口
     * */
    public DadaBaseResponse queryCityDetail(DadaBaseRequest request){
        request.setBody("");
        return executeRequest("/api/cityCode/list", request);
    }

    /**
     * 查询订单取消原因接口
     * */
    public DadaBaseResponse queryCancelReason(DadaBaseRequest request){
        request.setBody("");
        return executeRequest("/api/order/cancel/reasons", request);
    }

    /**
     * 测试环境模拟达达接单
     * */
    public DadaBaseResponse acceptOrder(DadaOrderStatusChangeTestRequest request){
        DadaBaseRequest dadaRequest = setBaseBody(request);
        return executeRequest("/api/order/accept", dadaRequest);
    }

    /**
     * 测试环境模拟达达取货
     * */
    public DadaBaseResponse fetch(DadaOrderStatusChangeTestRequest request){
        DadaBaseRequest dadaRequest = setBaseBody(request);
        return executeRequest("/api/order/fetch", dadaRequest);
    }

    /**
     * 测试环境模拟达达完成
     * */
    public DadaBaseResponse completed(DadaOrderStatusChangeTestRequest request){
        DadaBaseRequest dadaRequest = setBaseBody(request);
        return executeRequest("/api/order/finish", dadaRequest);
    }

    /**
     * 测试环境模拟达达取消
     * */
    public DadaBaseResponse cancel(DadaOrderStatusChangeTestRequest request){
        DadaBaseRequest dadaRequest = setBaseBody(request);
        DadaBaseResponse response=executeRequest("/api/order/cancel", dadaRequest);
        return response;
    }

    /**
     * 测试环境模拟订单过期
     * */
    public DadaBaseResponse expire(DadaOrderStatusChangeTestRequest request){
        DadaBaseRequest dadaRequest = setBaseBody(request);
        DadaBaseResponse response=executeRequest("/api/order/expire", dadaRequest);
        return response;
    }

    private DadaBaseResponse executeRequest(String url, DadaBaseRequest dadaRequest) {

        setBaseRequestParams(dadaRequest);
        //将request中的驼峰形式的属性转化为"_"形式的map
        Map<String , Object> dadaRequestMap = DadaUtils.entityTransToMap(dadaRequest);

        RequestBody body = RequestBody.create(JSON, toJson(dadaRequestMap));
        Request request = new Request.Builder()
                .url(dadaProperties.getServiceHost() + url)
                .post(body)
                .build();

        try {
            Response response = httpClient.newCall(request).execute();
            GSON.fromJson(response.body().string(), DadaBaseResponse.class);
        } catch (Exception e) {
            throw new DadaException("request data exception", e);
        }
        return null;
    }

    private void setBaseRequestParams(DadaBaseRequest request){
        request.setAppKey(dadaProperties.getAppKey());
        request.setSourceId(dadaProperties.getSourceId());
//        request.setAppKey("dada339f050d417cda9");
//        request.setSourceId("73753");

        Long s=System.currentTimeMillis();
        request.setTimestamp(s.toString());
        request.setV(DATA_API_VERSION);
        request.setFormat(FORMAT);
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
    private String toJson(Object object) {
        return GSON.toJson(object);
    }

    public void setDadaProperties(DadaProperties dadaProperties) {
        this.dadaProperties = dadaProperties;
    }

    public void setHttpClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
