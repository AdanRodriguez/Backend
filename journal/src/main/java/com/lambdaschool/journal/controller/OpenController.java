package com.lambdaschool.journal.controller;

import com.lambdaschool.journal.logging.Loggable;
import com.lambdaschool.journal.models.User;
import com.lambdaschool.journal.models.UserMinimum;
import com.lambdaschool.journal.models.UserRoles;
import com.lambdaschool.journal.service.RoleService;
import com.lambdaschool.journal.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Loggable
@RestController
public class OpenController
{
    private static final Logger logger = LoggerFactory.getLogger(OpenController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    // Create the user and Return the access token
    // POST http://localhost:2019/createnewuser
    // Just create the user
    // POST http://localhost:2019/createnewuser?access=false
    //
    // request body:
    // {
    //     "username" : "Mojo",
    //     "password" : "corgie",
    //     "primaryemail" : "home@local.house"
    // }

    @PostMapping(value = "/createnewuser",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewUser(HttpServletRequest httpServletRequest,
                                        @RequestParam(defaultValue = "true")
                                                boolean getaccess,
                                        @Valid
                                        @RequestBody UserMinimum newminuser) throws URISyntaxException
    {
        logger.trace(httpServletRequest.getMethod()
                .toUpperCase() + " " + httpServletRequest.getRequestURI() + " accessed");

        // Create the user
        User newuser = new User();

        newuser.setUsername(newminuser.getUsername());
        newuser.setPassword(newminuser.getPassword());
        newuser.setPrimaryemail(newminuser.getPrimaryemail());

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        newRoles.add(new UserRoles(newuser,
                // default role: user -- can change if necessary
                roleService.findByName("admin")));
        newuser.setUserroles(newRoles);

        newuser = userService.save(newuser);

        // set the location header for the newly created resource - to another controller!
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/users/user/{userId}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        String theToken = "";
        // this allows the user to create a new user and login in the same step
        // calls the login endpoint and returns the token -> TokenModel
        if (getaccess)
        {
            // return the access token
            RestTemplate restTemplate = new RestTemplate();
            String requestURI = "http://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/login";

            List<MediaType> acceptableMediaTypes = new ArrayList<>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(acceptableMediaTypes);
            headers.setBasicAuth(System.getenv("OAUTHCLIENTID"),
                    System.getenv("OAUTHCLIENTSECRET"));

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type",
                    "password");
            map.add("scope",
                    "read write trust");
            map.add("username",
                    newminuser.getUsername());
            map.add("password",
                    newminuser.getPassword());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map,
                    headers);

            theToken = restTemplate.postForObject(requestURI,
                    request,
                    String.class);
        } else
        {
            // nothing;
        }
        return new ResponseEntity<>(theToken,
                responseHeaders,
                HttpStatus.CREATED);
    }

    @ApiIgnore
    @GetMapping("favicon.ico")
    void returnNoFavicon()
    {
        logger.trace("favicon.ico endpoint accessed!");
    }
}