package so.sao.shop.dada.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.ReflectionUtils;
import so.sao.shop.dada.request.DadaBaseRequest;
import so.sao.shop.dada.request.DadaCreateShopRequest;
import so.sao.shop.dada.service.DadaService;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 达达生成签名工具
 * */
public class DadaUtils {

    public static final char UNDERLINE = '_';

    //在该工具类中维护一个缓存
    private static final ConcurrentMap<Class<?>, PropertyDescriptor[]> PROPERTY_DESCRIPTORS_CACHE = new ConcurrentHashMap<>();

    //json 序列化使用
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 生成签名
     * 签名生成的通用步骤如下：
     * 第一步：将参与签名的参数按照键值(key)进行排序。
     * 第二步：将排序过后的参数进行key value字符串拼接。
     * 第三步：将拼接后的字符串首尾加上app_secret秘钥，合成签名字符串。
     * 第四步：对签名字符串进行MD5加密，生成32位的字符串。
     * 第五步：将签名生成的32位字符串转换为大写。
     *
     * @param request
     * @return
     */
    public static String getSign(DadaBaseRequest request,String appSecret) {

        //首先将request实体类转化为map形式，并将实体类的驼峰形式转化为"_"形式
        Map<String , Object> map=entityTransToMap(request);

        //获得所有键值对
        Collection<String> keySet = map.keySet();
        List<String> list = new ArrayList<>(keySet);

        //拼接键值对字符串
        StringBuilder signStr = new StringBuilder();
        for (String key : list) {
            signStr.append(key).append(map.get(key));
        }

        //在字符串首尾加上app_secret
        String sign = appSecret + signStr.toString() + appSecret;

        //md5签名并后，转化为大写
        return DigestUtils.md5Hex(sign).toUpperCase(Locale.ENGLISH);
    }

    /**
     * 将实体类转化为map形式
     * */
    public static Map<String,Object> entityTransToMap(DadaBaseRequest request){

        //将不同的实体类放入currentHashMap不同的segment中当作缓存，下次相同的实体可以直接从缓存中获取其属性元素
        PropertyDescriptor[] propertyDescriptors = PROPERTY_DESCRIPTORS_CACHE.computeIfAbsent(request.getClass(), clazz -> {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
                return beanInfo.getPropertyDescriptors();
            } catch (IntrospectionException e) {
                throw new RuntimeException(e);
            }
        });

        //创建一个有序的map用于保存排序后的属性
        SortedMap<String, Object> map = new TreeMap<>();

        //对所有非空参数按AscII码表排序
        for (PropertyDescriptor pd : propertyDescriptors) {
            //反射get方法，得到属性对应的值
            Object param = ReflectionUtils.invokeMethod(pd.getReadMethod(), request);
            if (param != null && !pd.getName().equals("deliveryNo")) {
                //将下驼峰型的属性，转化为下划线（作为key与值param放入map）
                map.put(camelToUnderline(pd.getName()), param);
            }
            //达达漏洞deliveryNo不是下划线的形式
            else if(param != null && pd.getName().equals("deliveryNo")){
                map.put(pd.getName(), param);
            }
        }

        return map;

    }

    public static String toJson(Object object) {
        try {
            ObjectWriter writer = mapper.writerFor(object.getClass());
            return writer.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 将下划线形式的字符串转化为驼峰型
    private static String underlineToCamel(String param){
        if (param==null||"".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE){
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String []args) {
//        DadaBaseRequest request=new DadaBaseRequest();
//        request.setAppKey("dada339f050d417cda9");
//        request.setFormat("json");
//        request.setV("1.0");
//        Long s=System.currentTimeMillis();
//        request.setTimestamp(s.toString());
//        request.setSourceId("73753");
//        DadaAddOrderRequest addRequest = new DadaAddOrderRequest();
//        addRequest.setCityCode("021");
//        addRequest.setInfo("测试");
//        request.setBody(toJson(addRequest));

        //getSign(request);

        //Map<String , Object> map=entityTransToMap(request);

        //System.out.print(request.toString());
        DadaCreateShopRequest request1 = new DadaCreateShopRequest();
        request1.setOriginShopId("11111");
        request1.setAreaName("上海");
        request1.setContactName("小人");
        DadaCreateShopRequest request2 = new DadaCreateShopRequest();
        request2.setOriginShopId("222222");
        request2.setAreaName("上海");
        request2.setContactName("大人");
        ArrayList<DadaCreateShopRequest> list = new ArrayList<DadaCreateShopRequest>();
        list.add(request1);
        list.add(request2);


        DadaService service = new DadaService();
        service.createDadaShop(request1);
        //System.out.print();
    }
}
