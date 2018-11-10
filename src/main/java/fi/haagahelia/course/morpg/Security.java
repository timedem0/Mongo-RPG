package fi.haagahelia.course.morpg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.haagahelia.course.morpg.web.UserService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private UserService userService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
		.authorizeRequests()
			.antMatchers("/css/**", "/img/**").permitAll()
			.antMatchers("/", "/index", "/signup", "/saveuser", "/success", "/users", "/restful-upload").permitAll()
			.antMatchers("/restful/**").hasAuthority("ADMIN") 
		.anyRequest().authenticated()
		.and()
		.csrf().ignoringAntMatchers("/restful/api/**", "/restful-upload", "/upload")
		.and()
		.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/main")
			.permitAll()
			.and()
		.logout()
			.permitAll();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    } 
}
