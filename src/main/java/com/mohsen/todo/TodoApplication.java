package com.mohsen.todo;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}


	@Bean
	public GroupedOpenApi tweetsOpenApi(@Value("${springdoc.version}") String appVersion) {
		String[] paths = { "/tasks/**" };
		return GroupedOpenApi.builder().
				group("tasks")
				.addOpenApiCustomizer(openApi -> openApi.info(new Info().title("ToDo API").version(appVersion)))
				.pathsToMatch(paths)
				.build();
	}

}
