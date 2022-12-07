package uvt.ngo.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import uvt.ngo.rest.entity.UserType;

/*
This is just a mockup for the beta presentation!
 */
@Controller
public class AuthController {

    public UserType getUserType(final String apiKey) {
        return StringUtils.containsIgnoreCase("adminKey", apiKey) ? UserType.ADMIN : UserType.USER;
    }
}
