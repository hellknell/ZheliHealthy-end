package com.Yu.his.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2配置信息
 * 这里分了两组显示
 * 第一组是api，当作用户端接口
 * 第二组是admin，当作后台管理接口
 * 也可以根据实际情况来减少或者增加组
 *
 * @author Eric
 * @date 2023-07-30 22:17
 */

@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config {

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder().title("浙里健康项目整合Knife4j-API文档").description("本文档描述了SpringBoot如何整合Knife4j").version("1.0").contact(new Contact("Eric", "https://blog.csdn.net/weixin_47316183?type=blog", "ericsyn@foxmail.com")).build();
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder().title("浙里健康项目整合Knife4j-API文档").description("本文档描述了SpringBoot如何整合Knife4j").version("1.0").contact(new Contact("Eric", "https://blog.csdn.net/weixin_47316183?type=blog", "ericsyn@foxmail.com")).build();
    }

    /**
     * 第一组：api
     *
     * @return
     */
    @Bean
    public Docket webApiConfig() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("token").description("用户token")
                //.defaultValue(JwtHelper.createToken(1L, "admin"))
                .defaultValue("1").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        Docket webApi = new Docket(DocumentationType.SWAGGER_2).groupName("用户端接口").apiInfo(webApiInfo()).select()
                //只显示api路径下的页面
                .apis(RequestHandlerSelectors.basePackage("com.Yu.his.service.controller.user")).paths(PathSelectors.regex("/api/.*")).build().globalOperationParameters(pars);
        return webApi;
    }

    /**
     * 第二组：admin
     *
     * @return
     */
    @Bean
    public Docket adminApiConfig() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("token").description("用户token").defaultValue("1").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        Docket adminApi = new Docket(DocumentationType.SWAGGER_2).groupName("后台接口").apiInfo(adminApiInfo()).select()
                //只显示admin路径下的页面
                .apis(RequestHandlerSelectors.basePackage("com.Yu.his.service.controller.admin")).paths(PathSelectors.regex("/api/.*")).build().globalOperationParameters(pars);
        return adminApi;
    }

}

