package ir.co.sadad.promissory.services.daos;

import ir.co.sadad.promissory.entities.PromissoryLog;
import ir.co.sadad.promissory.repositories.PromissoryLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

/**
 * service for managing logs
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class PromissoryLogDaoService {
    private final MessageSource messageSource;
    private final PromissoryLogRepository promissoryLogRepository;

    /**
     * save logs in db
     *
     * @param eName      errorClass
     * @param eMsg       message
     * @param requestId  id of request - points to <code> PromissoryRequest </code>
     * @param jsonData   data that has exception
     * @param methodName name of caller method
     * @param status     status  of order
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLogs(String eName, String eMsg, String requestId, String jsonData, String methodName, String status) {

        String localizedMessage;
        try {
            localizedMessage = messageSource.getMessage(eMsg, null, Locale.of("fa"));
        } catch (NoSuchMessageException e) {
            localizedMessage = eMsg;
        }

        try {
            promissoryLogRepository.save(PromissoryLog
                    .builder()
                    .errorClass(eName)
                    .errorMessage(localizedMessage)
                    .requestId(requestId)
                    .methodName(methodName)
                    .serviceResponse(jsonData)
                    .status(status)
                    .build());
        } catch (Exception ex) {
            log.error("--------- error doesn't save into PromissoryLog table with message: {}", ex.getMessage());

        }

    }
}

