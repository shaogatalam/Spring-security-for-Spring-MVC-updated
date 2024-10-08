package base.pkg.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
// @CrossOrigin(origins = "http://localhost:3000",methods = {RequestMethod.GET, RequestMethod.POST}, allowedHeaders = {"Authorization", "Content-Type"},allowCredentials = "true")
//@Slf4j
public class LogoutController {

    @GetMapping("/logoutController")
    public String logout(HttpServletResponse response) {

        SecurityContextHolder.clearContext();

        // Delete the JWT token cookie
        Cookie jwtCookie = new Cookie("JwtToken", null);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        // Delete the refresh token cookie
        Cookie refreshJwtCookie = new Cookie("RefreshJwtToken", null);
        refreshJwtCookie.setMaxAge(0);
        refreshJwtCookie.setPath("/");
        response.addCookie(refreshJwtCookie);

        return "LogoutSuccess";
    }

}
