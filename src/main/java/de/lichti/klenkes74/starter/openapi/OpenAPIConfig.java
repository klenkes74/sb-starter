package de.lichti.klenkes74.starter.openapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.inject.Inject;

@Configuration
public class OpenAPIConfig {
    private final String title;
    private final String description;
    private final String version;
    private final String tosUrl;

    private final String devUrl;
    private final String qaUrl;
    private final String preProdUrl;
    private final String prodUrl;
    private final String localUrl;

    private final String email;
    private final String name;
    private final String url;

    private final String licenseName;
    private final String licenseUrl;


    @Inject
    public OpenAPIConfig(
        @Value("${application.name}") final String title,
        @Value("${application.version}") final String version,
        @Value("${application.description}") final String description,
        @Value("${application.tosUrl}") final String tosUrl,
        @Value("${application.license.name}") final String licenseName,
        @Value("${application.license.url}") final String licenseUrl,

        @Value("${application.owner.name}") final String name,
        @Value("${application.owner.url}") final String url,
        @Value("${application.owner.email}") final String email,

        @Value("${application.env.dev}") final String devUrl, 
        @Value("${application.env.qa}") final String qaUrl, 
        @Value("${application.env.preProd}") final String preProdUrl, 
        @Value("${application.env.prod}") final String prodUrl,
        @Value("${application.env.local:none}") final String localUrl
    ) {
        this.title = title;
        this.version = version;
        this.description = description;
        this.tosUrl = tosUrl;

        this.licenseName = licenseName;
        this.licenseUrl = licenseUrl;
        
        this.localUrl = localUrl;
        this.devUrl = devUrl;
        this.qaUrl = qaUrl;
        this.preProdUrl = preProdUrl;
        this.prodUrl = prodUrl;

        this.name = name;
        this.email = email;
        this.url = url;
    }

    @Bean
    public OpenAPI serviceOpenAPI() {
        return new OpenAPI()
            .info(generateInfo())
            .servers(generateServers())
            ;
    }

    private List<Server> generateServers() {
        if ("none".equalsIgnoreCase(localUrl)) {
            return List.of(
                generateServer(devUrl, "Development Stage"),
                generateServer(qaUrl, "QA Stage"),
                generateServer(preProdUrl, "Pre-Prod Stage"),
                generateServer(prodUrl, "Production Stage")
            );
        } else {
            return List.of(
                generateServer(devUrl, "Development Stage"),
                generateServer(qaUrl, "QA Stage"),
                generateServer(preProdUrl, "Pre-Prod Stage"),
                generateServer(prodUrl, "Production Stage"),
                generateServer("http://localhost:8080", "Localhost, Port 8080")
            );
        }
    }

    private Info generateInfo() {
        final Info result = new Info();

        result.setContact(generateContact());
        result.setTitle(title);
        result.setDescription(description);
        result.setVersion(version);
        result.setLicense(generateLicense());
        result.setTermsOfService(tosUrl);

        return result;
    }

    private Contact generateContact() {
        final Contact result = new Contact();

        result.setName(name);
        result.setEmail(email);
        result.setUrl(url);
        
        return result;
    }

    private License generateLicense() {
        return new License()
            .name(licenseName)
            .url(licenseUrl)
            ;
    }

    private Server generateServer(final String url, final String description) {
        Server result = new Server();

        result.setUrl(url);
        result.setDescription(description);

        return result;
    }
}
