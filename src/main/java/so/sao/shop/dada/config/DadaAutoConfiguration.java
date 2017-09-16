package so.sao.shop.dada.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import okhttp3.OkHttpClient;
import so.sao.shop.dada.service.DadaService;


@Configuration
@EnableConfigurationProperties(DadaProperties.class)
@ConditionalOnProperty(prefix = "shop.dada", name = { "app-key", "app-secret", "service-host", "source-id" })
public class DadaAutoConfiguration {

    @Autowired
    private DadaProperties properties;

    @Bean
    public DadaService dadaService(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        DadaService dadaService = new DadaService();
        dadaService.setDadaProperties(properties);
        dadaService.setHttpClient(okHttpClient);
        return dadaService;
    }

}
