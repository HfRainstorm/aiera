package cn.hfstorm.aiera.common.core.component;

import cn.hfstorm.aiera.common.core.properties.AiEraProjectProps;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author hmy
 * @date : 2025/2/8 10:33
 */
@Configuration
@ConditionalOnBean({AiEraProjectProps.class})
//@ConditionalOnBean({BuildProperties.class})
public class SwaggerConfig {

    //    AiEraProjectProps aiEraProjectProps;
    @Autowired
    private AiEraProjectProps aiEraProjectProps;

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AI Era接口文档")
                        .contact(new Contact())
                        .description("AI时代信息管理平台提供的 RESTful API")
                        .version(aiEraProjectProps.getVersion())
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/")))
                .externalDocs(new ExternalDocumentation()
                        .description("外部文档")
                        .url(aiEraProjectProps.getUrl()));
    }
}
