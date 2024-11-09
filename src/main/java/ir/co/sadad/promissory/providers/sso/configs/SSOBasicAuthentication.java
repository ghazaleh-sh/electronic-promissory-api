package ir.co.sadad.promissory.providers.sso.configs;

import feign.auth.BasicAuthRequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Slf4j
public class SSOBasicAuthentication {

    @Value("${sso.client_id}")
    String clientName;

    @Value("${sso.client_sec}")
    String clientPassword;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        log.info("client name: {}", clientName);
        log.info("client family: {}", clientPassword);
        return new BasicAuthRequestInterceptor(clientName, clientPassword);
    }
}
