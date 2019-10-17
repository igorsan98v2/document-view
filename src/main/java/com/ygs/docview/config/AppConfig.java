package com.ygs.docview.config;

import com.ygs.docview.controller.DocRestfulAPI;
import com.ygs.docview.service.DeleteService;
import com.ygs.docview.service.DownloadService;
import com.ygs.docview.service.EditService;
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


        @Bean
        public EditService editService(){
            return new EditService();
        }

        @Bean
        public DownloadService downloadService(){
            return new DownloadService();
        }
        @Bean
        public DeleteService deleteService(){
            return new DeleteService();
        }

}
