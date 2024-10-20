package ir.co.sadad.promissory.providers.sso.services;

import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.dtos.sso.SSOTokenDto;
import ir.co.sadad.promissory.providers.sso.SSOClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

import static ir.co.sadad.promissory.commons.Constants.BEARER__PREFIX;

@Slf4j
@RequiredArgsConstructor
@Service
public class SSOTokenService {

    private static String accessToken;
    /**
     * valid date is in second
     */
    private static Long validDate;

    @Value(value = "${client.sso.scope}")
    private String scope;

    private final SSOClient ssoClient;


    public String getToken() {
        if (isTokenEmpty() || isTokenShouldBeRefreshed()) {
            callLogin();
        }
        return accessToken;
    }

    static synchronized boolean isTokenShouldBeRefreshed() {
        return (validDate - 2 * 60) < System.currentTimeMillis() / 1000;
    }

    static synchronized boolean isTokenEmpty() {
        return validDate == null
                || accessToken.isEmpty();
    }

    private void callLogin() {
        try {
            SSOTokenDto ssoToken =
                    ssoClient.geToken(Map.of("scope", scope, "grant_type", "client_credentials"));

            validDate = System.currentTimeMillis() / 1000 + ssoToken.getExpiresIn();
            accessToken = BEARER__PREFIX + ssoToken.getAccessToken();

        } catch (Exception e) {
            log.error("SSO LOGIN SERVICE ERROR IS >>>>>>>>> {0}", e);
            throw new PromissoryException("BUSINESS_LOGIN_UNKNOWN_PROBLEM", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
