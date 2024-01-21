package de.lichti.klenkes74.starter.logging;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.access.servlet.TeeFilter;
import ch.qos.logback.access.tomcat.LogbackValve;
import jakarta.servlet.Filter;

@Configuration
public class AccessLogConfiguration {
    @Bean(name = "TeeFilter")
    public Filter teeFilter() {
        return new TeeFilter();
    }

    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory result = new TomcatServletWebServerFactory();

        result.addContextValves(new LogbackValve());

        return result;
    }
}
