package ir.co.sadad.promissory.providers.icms;

import ir.co.sadad.promissory.dtos.icms.LegalCustomerInfoDto;
import ir.co.sadad.promissory.providers.icms.configs.ICMSClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ICMSClient", url = "${client.icms.base-url}", configuration = {ICMSClientConfig.class})
public interface ICMSClient {

    @RequestMapping(method = RequestMethod.GET, value = "${client.icms.legal-customer-info}")
    LegalCustomerInfoDto getLegalCustomerInfo(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("uniqueIdentifier") String uniqueIdentifier);
}
