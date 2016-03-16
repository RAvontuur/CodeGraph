package nl.rav.codegraph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rene on 13-3-16.
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan
public class WebRunner {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebRunner.class, args);
    }

}
