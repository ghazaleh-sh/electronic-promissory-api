package ir.co.sadad.promissory.providers.sso;

import ir.co.sadad.promissory.dtos.sso.SSOTokenDto;
import ir.co.sadad.promissory.providers.sso.configs.SSOBasicAuthentication;
import ir.co.sadad.promissory.providers.sso.configs.SSOClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "SSOClient", url = "${client.sso.base-url}", configuration = {SSOClientConfig.class, SSOBasicAuthentication.class})
public interface SSOClient {

    @RequestMapping(
            method = RequestMethod.POST,
            value = "${client.sso.get-token-path}",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    SSOTokenDto geToken(@RequestBody Map<String, ?> form);
}
