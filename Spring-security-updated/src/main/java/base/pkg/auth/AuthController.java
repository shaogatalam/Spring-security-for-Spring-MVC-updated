package base.pkg.auth;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import base.pkg.model.UserModel;
import base.pkg.repo.UserRepository;

@RestController
//@CrossOrigin(methods = {RequestMethod.POST},allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    public UserRepository userRepository;
    
    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request, HttpServletResponse response){
        authenticationService.register(request,response);

    }

    //@PostMapping("/auth")
    @PostMapping("/login")
    public UserModel auth(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        return authenticationService.authenticate(request,response);
    }

}
