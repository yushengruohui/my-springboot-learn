package com.ys.swagger.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket generatorRestControllerAPI(Environment environment) {
        //配置Swagger的Docket的bean实例
        System.out.println("environment = " + environment);
        //设置要显示Swagger的系统运行环境
        Profiles profiles = Profiles.of("dev", "test");
        System.out.println("profiles = " + profiles);
        //通过environment.acceptsProfiles 判断是否处在自己设定的环境当中
        boolean flag = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(flag)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ys.swagger.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置swagger信息
     *
     * @return 接口信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("xxx项目的接口文档")
                .description("规范且实时更新的接口文档，方便前后端交流及维护")
                .termsOfServiceUrl("http://localhost:8080")
                .contact(new Contact("yusheng", "http://localhost:8080", "2091311046@qq.com"))
                .version("1.0")
                .build();
    }
}
