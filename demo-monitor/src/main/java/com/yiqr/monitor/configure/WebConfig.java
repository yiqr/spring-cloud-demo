package com.yiqr.monitor.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: yiqr
 * @Date: 2021/8/26 5:16 下午
 * @Description:
 */
@EnableWebSecurity
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("yiqr").password("123123").roles("admin").build());
        return manager;
    }
}
