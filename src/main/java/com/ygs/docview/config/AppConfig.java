package com.ygs.docview.config;

import com.ygs.docview.service.UploadService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

        @Bean
        public UploadService uploadService() {
            return new UploadService();
            // instantiate, configure and return bean ...
        }


}
