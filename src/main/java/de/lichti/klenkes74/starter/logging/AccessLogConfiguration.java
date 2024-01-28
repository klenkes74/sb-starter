package de.lichti.klenkes74.starter.logging;

import org.apache.catalina.Context;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.access.servlet.TeeFilter;
import ch.qos.logback.access.tomcat.LogbackValve;
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AccessLogConfiguration {
    private static final String ACCESS_LOG = "ACCESS_LOG";
    private static final String FILE_NAME = "conf/logback-access.xml";

    @Bean(name = "TeeFilter")
    public Filter teeFilter() {
        final TeeFilter result = new TeeFilter();

        log.debug("Adding TeeFilter to web container. filter={}", result);
        return result;
    }

    @Bean
    public TomcatContextCustomizer accessLogging() {
        final TomcatContextCustomizer result = new TomcatContextCustomizer() {
            @Override
            public void customize(final Context context) {
                final LogbackValve logbackValve = new LogbackValve();
                logbackValve.setName(ACCESS_LOG);
                logbackValve.setFilename(FILE_NAME);
                logbackValve.setAsyncSupported(true);
                logbackValve.setQuiet(true);

                context.getPipeline().addValve(logbackValve);
            }
        };

        log.debug("Adding access_log style output. contextCustomizer={}", result);
        return result;
    }
}
