package ir.co.sadad.promissory.services;

import ir.co.sadad.promissory.commons.enums.RequestStatus;
import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.dtos.promissory.CancelRequestReqDto;
import ir.co.sadad.promissory.dtos.promissory.PromissoryClientResponseDto;
import ir.co.sadad.promissory.entities.PromissoryRequest;
import ir.co.sadad.promissory.providers.promissory.PromissoryClient;
import ir.co.sadad.promissory.providers.sso.services.SSOTokenService;
import ir.co.sadad.promissory.services.daos.PromissoryLogDaoService;
import ir.co.sadad.promissory.services.daos.PromissoryRequestDaoService;
import ir.co.sadad.promissory.services.utils.DataConverter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static ir.co.sadad.promissory.commons.Constants.TERMINAL_ID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromissoryBaseServiceImpl implements PromissoryBaseService {

    private final PromissoryClient promissoryClient;
    private final SSOTokenService promissoryTokenService;
    private final PromissoryRequestDaoService requestDaoService;
    private final PromissoryLogDaoService logDaoService;


    @SneakyThrows
    @Override
    public void cancelRequest(String requestId) {
        try {
            PromissoryClientResponseDto<Void> cancelRes = promissoryClient.cancelRequest(promissoryTokenService.getToken(),
                    TERMINAL_ID,
                    CancelRequestReqDto.builder()
                            .requestId(requestId)
                            .build());

            PromissoryRequest savedRequest = requestDaoService.getRequestBy(requestId);
            requestDaoService.updatePromissoryRequestStatus(savedRequest, RequestStatus.CANCELED);

            logDaoService.saveLogs("", "",
                    requestId, DataConverter.convertResponseToJson(cancelRes), "cancelRequest", "SUCCESSFUL");

        } catch (Exception e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    requestId,
                    e instanceof PromissoryException ? ((PromissoryException) e).getJsonError() : null,
                    "cancelRequest", "EXCEPTION");
            log.error("exception in cancel request service: {}", e.getMessage());
        }

    }
}
