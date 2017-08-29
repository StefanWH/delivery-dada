package so.sao.shop.dada.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import so.sao.shop.dada.service.DadaService;


@Configuration
@EnableConfigurationProperties(DadaProperties.class)
@ConditionalOnProperty(prefix = "shop.dada", name = { "app-key", "app-secret", "service-host", "source-id" })
public class DadaAutoConfiguration {

    @Autowired
    private DadaProperties properties;

    @Bean
    public DadaService dadaService(RestTemplateBuilder restTemplateBuilder){

        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
        factory.setReadTimeout(10000);//设置超时时间

        RestTemplate wechatPaymentRestTemplate = restTemplateBuilder
                .rootUri(properties.getServiceHost())
                .requestFactory(factory)
                .build();

        DadaService dadaService = new DadaService();
        dadaService.setDadaRestTemplate(wechatPaymentRestTemplate);
        dadaService.setDadaProperties(properties);
        return dadaService;
    }

}
