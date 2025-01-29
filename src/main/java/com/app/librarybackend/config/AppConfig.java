package com.app.librarybackend.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {
  @Bean
  public Mapper mapper() {
    return new DozerBeanMapper();
  }

  @Bean
  public ValidatingMongoEventListener validatingMongoEventListener(
      final LocalValidatorFactoryBean factory) {
    return new ValidatingMongoEventListener(factory);
  }

  @Bean
  public FilterRegistrationBean corsFilterRegistration(){
    UrlBasedCorsConfigurationSource source = new
        UrlBasedCorsConfigurationSource();
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    configuration.addAllowedOrigin("http://localhost:3000/");
    List<String> allowedHeaders = new ArrayList<>();
    allowedHeaders.add("*");
    List<String> allowedMethods = new ArrayList<>();
    allowedMethods.add("*");
    addAllowedHeaders(allowedHeaders);
    addAllowedMethods(allowedMethods);

    configuration.setAllowedHeaders(allowedHeaders);
    configuration.setAllowedMethods(allowedMethods);

    //time when the request is accepted
    configuration.setMaxAge(4000L);

    source.registerCorsConfiguration("/**", configuration);
    FilterRegistrationBean registrationBean = new FilterRegistrationBean(new CorsFilter(source));
    registrationBean.setOrder(-102);
    return registrationBean;
  }

  private void addAllowedHeaders(List<String> allowedHeaders) {
    allowedHeaders.add(HttpHeaders.AUTHORIZATION);
    allowedHeaders.add(HttpHeaders.CONTENT_TYPE);
    allowedHeaders.add(HttpHeaders.ACCEPT);
  }

  private void addAllowedMethods(List<String> allowedMethods) {
    allowedMethods.add(HttpMethod.POST.name());
    allowedMethods.add(HttpMethod.GET.name());
    allowedMethods.add(HttpMethod.PUT.name());
  }
}
