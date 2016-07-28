package com.sensorberg.backend;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import static java.util.Arrays.asList;

@Data
@SpringBootApplication
public class Application implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(Application.class);
        Environment env = app.run(args).getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                         "Application '{}' is running! Access URLs:\n\t" +
                         "Local: \t\thttp://127.0.0.1:{}\n\t" +
                         "External: \thttp://{}:{}\n----------------------------------------------------------",
                        env.getProperty("spring.application.name"),
                        env.getProperty("server.port"),
                        InetAddress.getLocalHost().getHostAddress(),
                        env.getProperty("server.port"));
    }

    @Autowired
    private Environment env;


    @PostConstruct
    public void initApplication() throws Exception {
        log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        Collection<String> activeProfiles = asList(env.getActiveProfiles());
        if (activeProfiles.contains("dev") && activeProfiles.contains("prod")) {
            String msg = "You have misconfigured your application! It should not run " +
                              "with both the 'dev' and 'prod' profiles at the same time.";
            log.error(msg);
            throw new IllegalStateException(msg);
        }
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
