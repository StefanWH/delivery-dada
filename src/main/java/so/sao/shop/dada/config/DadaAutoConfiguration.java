package so.sao.shop.dada.config;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import so.sao.shop.dada.service.DadaService;

@Configuration
@EnableConfigurationProperties(DadaProperties.class)
@ConditionalOnProperty(prefix = "shop.dada", name = { "app-key", "app-secret", "service-host", "source-id" })
public class DadaAutoConfiguration {

    @Autowired
    private DadaProperties properties;

    @Bean
    public DadaService dadaService(HttpClient httpClient, RestTemplateBuilder restTemplateBuilder){

        DadaService dadaService = new DadaService();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate wechatPaymentRestTemplate = restTemplateBuilder.rootUri(properties.getServiceHost())
                .requestFactory(requestFactory).build();
        dadaService.setDadaRestTemplate(wechatPaymentRestTemplate);
        dadaService.setDadaProperties(properties);

        return dadaService;
    }

}
