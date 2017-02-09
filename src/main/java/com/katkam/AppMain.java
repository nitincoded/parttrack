package com.katkam;

import com.katkam.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created by Developer on 2/10/17.
 */
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
//(exclude = SocialWebAutoConfiguration.class)
//@EnableAutoConfiguration
@Import(AppConfig.class)
@ComponentScan("com.katkam")
public class AppMain {
    public static void main(String args[]) throws Exception {
        SpringApplication.run(AppMain.class, args);
    }
}