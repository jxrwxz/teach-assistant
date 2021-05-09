package jxrwxz.teachassistant.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/d:/images/**").addResourceLocations("file:D:/myProjects/images/");
    }
}

@SpringBootApplication
class TeachAssistantApplication implements WebMvcConfigurer{

    public static void main(String[] args){
        SpringApplication.run(TeachAssistantApplication.class,args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
    }
}
