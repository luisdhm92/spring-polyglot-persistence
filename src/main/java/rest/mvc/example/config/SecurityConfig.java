package rest.mvc.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_MATCHERS = {"h2-console/**", "customers/**", "categories/**", "/swagger" +
            "-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**"};
    private static final String[] PUBLIC_MATCHERS_GET = {};
    @Autowired
    private Environment env;

    @Override
    public final void configure(HttpSecurity http) throws Exception {
        if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
            http.headers().frameOptions().disable();
        }
        http.cors().and().csrf().disable();
        http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll();
        //.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
        //.anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
