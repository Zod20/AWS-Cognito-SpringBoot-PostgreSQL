package com.zod.SpringBootHibernate;

import static com.nimbusds.jose.JWSAlgorithm.RS256;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.zod.SpringBootHibernate.security.JwtConfiguration;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootPostgresqlHibernateRestApplication {

    @Autowired
    private JwtConfiguration jwtConfiguration;

    public static void main(String[] args) throws InterruptedException, MalformedURLException {

        SpringApplication.run(SpringBootPostgresqlHibernateRestApplication.class, args);

    }

    @Bean
    public ConfigurableJWTProcessor configurableJWTProcessor() throws MalformedURLException {
        ResourceRetriever resourceRetriever
                = new DefaultResourceRetriever(jwtConfiguration.getConnectionTimeout(),
                        jwtConfiguration.getReadTimeout());
        URL jwkSetURL = new URL(jwtConfiguration.getJwkUrl());
        JWKSource keySource = new RemoteJWKSet(jwkSetURL, resourceRetriever);
        ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();
        JWSKeySelector keySelector = new JWSVerificationKeySelector(RS256, keySource);
        jwtProcessor.setJWSKeySelector(keySelector);
        return jwtProcessor;
    }

}
