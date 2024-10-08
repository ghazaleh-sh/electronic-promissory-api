package ir.co.sadad.promissory.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.co.sadad.promissory.dtos.UserCertificationResDto;
import ir.co.sadad.promissory.dtos.icms.LegalCustomerInfoDto;
import ir.co.sadad.promissory.services.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${v1API}/user")
@Tag(description = "سرویس های مربوط به گواهی کاربر و ایران ساین", name = "certification services")
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;


    @Operation(summary = "سرویس دریافت گواهی کاربر")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserCertificationResDto.class)))
    @GetMapping("/certification")
    public ResponseEntity<UserCertificationResDto> userCertification(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken) {
        return new ResponseEntity<>(certificationService.userCertifications(authToken, null), HttpStatus.OK);
    }

    @Operation(summary = "سرویس دریافت اطلاعات مشتری حقوقی")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LegalCustomerInfoDto.class)))
    @GetMapping("/legal-info/{identifier}")
    public ResponseEntity<LegalCustomerInfoDto> getLegalCustomerInfo(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                                     @PathVariable String identifier) {
        return new ResponseEntity<>(certificationService.getLegalCustomerInfo(authToken, identifier), HttpStatus.OK);
    }
}
