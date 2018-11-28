package org.myaldoc.authorizationserver.configuration.handlers;

import org.myaldoc.authorizationserver.connection.exceptions.ConnectionExceptionMessages;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 25/11/2018
 * @Class purposes : .......
 */

@Component
public class ConnexionFailureHandler implements AuthenticationFailureHandler {

    /**
     * CONSTANTES
     **/
    public static final String BAD_CREDENTIALS = "bad_credentials";
    public static final String INTERNAL_ERROR = "internal_error";
    public static final String AUTH_LOGIN_ERROR = "/auth/login?error=";

    /**
     * MESSAGES D'ERREUR
     **/
    private ConnectionExceptionMessages exceptionMessages;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String redirectUrl = AUTH_LOGIN_ERROR;
        if (e instanceof BadCredentialsException) redirectUrl = redirectUrl + BAD_CREDENTIALS;
        else if (response.getStatus() == 500) redirectUrl = redirectUrl + INTERNAL_ERROR;

        response.sendRedirect(redirectUrl);
    }

    public String getErrorMessage(String code) {
        String message = "";

        switch (code) {
            case BAD_CREDENTIALS:
                message = this.exceptionMessages.getBadCredentials();
                break;
            case INTERNAL_ERROR:
                message = this.exceptionMessages.getInternalError();
                break;
            default:
                message = this.exceptionMessages.getDefaultMessage();
        }
        return message;
    }
}
