package com.josevalenzuela.apiprospectos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Controller
@SpringBootApplication
public class ApiProspectosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiProspectosApplication.class, args);
	}
}
