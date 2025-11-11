package com.albymens.task_management.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management API")
                        .version("1.0")
                        .description("""
                        API for Managing tasks which supports ✅ task prioritization,📌 status tracking,🔍 filtering, and 🔒 secure JWT authentication.
                        
                        📂 **Source Code**: [🛠️GitHub Repository](https://github.com/Albymens/Task-Management-API.git)
                        
                        ### ✅ **Testing Flow**
                        Note: To test each API click on the API, then the "Try it out button" to enable the inputs.
                        1. **Register a User**
                        2. **Login(with 'Register' credentials ) to Obtain a JWT Token**
                        3. Click the **Authorize** button (top-right of Swagger UI)
                        4. Paste the JWT token to authenticate
                        5. Proceed with API requests to create, update, delete, retrieve all, and filter tasks by priority, status, and deadline.
                        """))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
