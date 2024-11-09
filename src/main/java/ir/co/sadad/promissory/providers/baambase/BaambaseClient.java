package ir.co.sadad.promissory.providers.baambase;

import ir.co.sadad.promissory.dtos.baambase.*;
import ir.co.sadad.promissory.providers.baambase.configs.BaambaseClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(value = "BaambaseClient", url = "${client.baambase.base-url}", configuration = {BaambaseClientConfig.class})
public interface BaambaseClient {

    @RequestMapping(method = RequestMethod.GET, value = "${client.baambase.user-certifications-path}")
    BaamBaseResponseDto<List<CertificationResBodyDto>> userCertification(
            @RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "${client.baambase.encoded-certifications-path}")
    BaamBaseResponseDto<CertificateEncodedResDto> getEncodedCertification(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("certificateKeyId") String certificateKeyId);

    @RequestMapping(method = RequestMethod.GET, value = "${client.baambase.data-toBe-Sign-path}")
    BaamBaseResponseDto<DataToBeSignResDto> dataToBeSignGeneration(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody DataToBeSignReqDto dataToBeSignReqDto);
}
