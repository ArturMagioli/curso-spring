package com.magioli.jobportal.client.config;

import com.magioli.jobportal.client.service.TodoService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(types = {TodoService.class})
public class HttpServiceClient {
}
