package base.pkg.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
// import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import base.pkg.repo.UserRepository;

@Primary
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

        @Autowired
        public JwtAuthenticationFilter jwtAuthFilter;
        @Autowired
        public AuthenticationProvider authenticationProvider;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        public JwtService jwtService;

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                
                http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/permitted").authenticated()
                        //.requestMatchers("/register", "/authenticate").permitAll()
                        //.anyRequest().authenticated()
                        .anyRequest().permitAll()
                )
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();

        }

        // @Bean
        // CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        //         return new CustomAuthenticationSuccessHandler(jwtService, userRepository);
        // }

        // @Bean
        // public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        //     return auth.build();
        // }

}
