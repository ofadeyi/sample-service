package uk.co.whitbread.sample;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import uk.co.whitbread.sample.model.SampleRequest;
import uk.co.whitbread.sample.model.SampleResponse;

import javax.servlet.http.HttpServletResponse;

import static com.google.common.base.Predicates.not;

@EnableSwagger2
@EnableDiscoveryClient
@EnableZuulProxy
@EnableCaching
@EnableAsync
@SpringBootApplication
public class SampleServiceApplication {

	@Value("${swagger.api.version}")
	String swaggerApiVersion;

	@Bean
	public Docket swaggerApiDocumentation(){
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
				.groupName("Api")
				.ignoredParameterTypes(HttpServletRequest.class, HttpServletResponse.class)
				.apiInfo(apiInfo())
				.ignoredParameterTypes(SampleRequest.class)
				.select()
				.apis(RequestHandlerSelectors.basePackage("uk.co.whitbread.sample"))
				.build()
				.pathMapping("/");
	}

	@Bean
	public Docket swaggerInternalDocumentation(){
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Internal")
				.select()
				.apis(not(RequestHandlerSelectors.basePackage("uk.co.whitbread.sample")))
				.build()
				.pathMapping("/hotelbooking")
				.tags(new Tag("Status endpoints", "All endpoints relating to the status of the Microservice"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Sample API Documentation")
				.description("<p>TBU</p>")
				.version(swaggerApiVersion)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleServiceApplication.class, args);
	}
}
