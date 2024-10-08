package base.pkg.auth;
import base.pkg.config.JwtService;
import base.pkg.entity.UserDetailsImplementation;
import base.pkg.entity.User;
import base.pkg.model.UserModel;
import base.pkg.repo.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthenticationService {

    @Autowired
    public UserRepository userRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    public JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public Object register(RegisterRequest request, HttpServletResponse response) {
        var optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            System.out.println("Exist");
            return "Exist"; // User already exists
        } else {            // User does not exist, proceed with registration
            User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .build();

            //original-> Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

            // test
            UserDetailsImplementation userDetailsImplementation = new UserDetailsImplementation(user);
            Collection<? extends GrantedAuthority> authorities = userDetailsImplementation.getAuthorities();

            System.out.println(authorities);
            userRepository.save(user);
            return user;
        }
    }


    public UserModel authenticate(AuthenticationRequest request, HttpServletResponse response) {

        try {

            System.out.println(request.getUsername());
            System.out.println(request.getPassword());

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

            if (authentication.isAuthenticated()) {

                // test
                User user                               = userRepository.findByEmail(request.getUsername()).orElseThrow();
                UserDetails userDetails                 = new UserDetailsImplementation(user);
                var jwtToken                            = jwtService.generateToken(userDetails);
                var refreshToken                        = jwtService.generateRefreshToken(userDetails);

                //bellow - original
                //User user = userRepository.findByEmail(request.getUsername()).orElseThrow();
                //var jwtToken      = jwtService.generateToken(user);
                //var refreshToken  = jwtService.generateRefreshToken(user);

                UserModel userModel = new UserModel();
                userModel.setFirstName(user.getFirstName());
                userModel.setLastName(user.getLastName());
                userModel.setEmail(user.getEmail());
                userModel.setRole(user.getRole());

                Cookie jwtCookie = new Cookie("JwtToken", jwtToken);
                jwtCookie.setHttpOnly(true);
                jwtCookie.setSecure(true);          // Set 'Secure' flag for HTTPS-only transmission
                jwtCookie.setPath("/");              // Set the cookie path as per your requirements
                response.addCookie(jwtCookie);

                Cookie RjwtCookie = new Cookie("RefreshJwtToken", refreshToken);
                RjwtCookie.setHttpOnly(true);
                RjwtCookie.setSecure(true);
                RjwtCookie.setPath("/");
                response.addCookie(RjwtCookie);

                //SecurityContextHolder.getContext().setAuthentication(authentication);

                try {
                    response.sendRedirect("/profile");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return userModel;
            }

        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }

        //return null; 
        throw new RuntimeException("Authentication failed");
    }

}
