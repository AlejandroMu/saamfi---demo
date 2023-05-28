package co.edu.icesi.dev.saamfi.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import co.edu.icesi.dev.saamfi.saamfisecurity.filters.SaamfiAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class DemoSecurityConf extends WebSecurityConfigurerAdapter {

    @Value("${saamfi.url}")
    private String saamfiUrl;

    @Value("${saamfi.system.id}")
    private String systemId;

    @Value("${saamfi.institution.id}")
    private String institution;

    @Bean
    public SaamfiAuthenticationFilter getSaamfiAuthenticationFilter() {
        return new SaamfiAuthenticationFilter(saamfiUrl,Long.parseLong(systemId),Long.parseLong(institution));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Configuration of cors and csfr policies. Configuration of authorized paths
        // without authorization.
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/public/**")
                .permitAll().anyRequest().authenticated().and().sessionManagement()

                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Filter to handle token authorized request with saamfi service
        http.addFilterBefore(getSaamfiAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}
