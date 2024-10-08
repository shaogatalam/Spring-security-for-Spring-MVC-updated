package base.pkg.controller;
import base.pkg.config.JwtService;
import base.pkg.entity.User;
import base.pkg.model.UserModel;
import base.pkg.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

//import java.util.Collection;

//@Slf4j
@RestController
// @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST}, allowedHeaders = {"Authorization", "Content-Type"}, allowCredentials = "true")
public class ProfileController {
    @Autowired
    public JwtService jwtService;

    @Autowired
    public UserRepository userRepository;

    @GetMapping ("/profile")
    public String getProfile(
                @CookieValue(name = "JwtToken", required = false) String Token,
                @CookieValue(name = "RefreshJwtToken", required = false) String RToken,
                HttpServletRequest request
            )
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username               = authentication.getName();
        //String role                   = authentication.getAuthorities().iterator().next().getAuthority();

        //        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        //        System.out.println("Role: " + role);
        //        System.out.println("Permissions:");
        //        for (GrantedAuthority authority : authorities) {
        //            System.out.println(authority.getAuthority());
        //        }

        User user = userRepository.findByEmail(username).orElseThrow();
        UserModel userModel = new UserModel();
        userModel.setEmail(user.getEmail());
        userModel.setRole(user.getRole());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());

        //return userModel;
        return "profile page :: " + userModel.toString();

    }

}
