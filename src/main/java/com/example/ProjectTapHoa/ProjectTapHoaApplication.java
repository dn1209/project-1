package com.example.ProjectTapHoa;

import com.example.ProjectTapHoa.services.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProjectTapHoaApplication implements WebMvcConfigurer{
    @Autowired
	SecurityInterceptor securityInterceptor;
	public static void main(String[] args) {
		SpringApplication.run(ProjectTapHoaApplication.class, args);
	}
    @Override
        public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor);
    }
    


}
