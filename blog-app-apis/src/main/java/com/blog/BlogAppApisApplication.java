package com.blog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info =
		@Info(
				title = "Blog project App.",
				description = "this project about manage the blogs",
				version = "3.0",
				contact =
				@Contact(
						name = "Praveen",
						email = "praveen@gmail.com"
				),
				license =
				@License(
						name = "spring boot"
				)
		)
)
public class BlogAppApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}


}
