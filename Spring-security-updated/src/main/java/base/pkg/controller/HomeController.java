package base.pkg.controller;

import base.pkg.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

//origins = "http://localhost:3000",
@RestController
// @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST}, allowedHeaders = {"Authorization", "Content-Type"},allowCredentials = "true")
//@Slf4j
//@CrossOrigin
public class HomeController {

    @Autowired
    public JwtService jwtService;


    @GetMapping("/home")
    public Collection<?> HomeCon(HttpServletRequest request) {

        Authentication authentication       = SecurityContextHolder.getContext().getAuthentication();
        Collection<?> authorities = null;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal                = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails     = (UserDetails) principal;
                String username             = userDetails.getUsername();
                authorities                 = userDetails.getAuthorities();
            } else {
                String username = principal.toString();
            }
        }

        return authorities;
    }

    
    @GetMapping("/permitted")
    public Object permitted(HttpServletRequest request){
        
        String username = "cookie";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            //String username_ = userDetails.getUsername();
            //return username_;
            return userDetails;
        } else {
        }
        return username;
    }
}
