package com.lambdaschool.journal.controller;


import com.lambdaschool.journal.logging.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;


@Loggable
@Controller // not a REST controller -- user authentication is separate from the REST API
public class LogoutController
{
    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);
    @Autowired
    private TokenStore tokenStore;

    // go to either one of these endpoints with an oauth token and it will remove the token from the token store
    @RequestMapping(value = {"/oauth/revoke-token", "/logout"},
                    method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request)
    {
        logger.trace(request.getMethod()
                .toUpperCase() + " " + request.getRequestURI() + " accessed");

        String authHeader = request.getHeader("Authorization");
        // checks if token is valid -- if it is, remove it from the token store
        if (authHeader != null)
        {
            String tokenValue = authHeader.replace("Bearer",
                    "")
                    .trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
    }
}