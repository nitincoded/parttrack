package com.katkam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Developer on 2/10/17.
 */
@Configuration
@Import({SecurityConfig.class, MvcConfig.class})
public class AppConfig {
}
