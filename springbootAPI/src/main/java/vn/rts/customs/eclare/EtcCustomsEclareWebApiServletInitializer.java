package vn.rts.customs.eclare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import vn.rts.customs.lib.SpringBootServletInitializerImpl;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@ComponentScan(basePackages = { "vn.rts.customs.lib", "vn.rts.customs.eclare" })
@EntityScan("vn.rts.customs.eclare")
public class EtcCustomsEclareWebApiServletInitializer extends SpringBootServletInitializerImpl {

	public static void main(String[] args) {
		SpringApplication.run(EtcCustomsEclareWebApiServletInitializer.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EtcCustomsEclareWebApiServletInitializer.class);
	}

	@Bean
	public Docket apiEclareStq() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("rts-customs-eclare-webapi").select()
				.apis(Predicates.or(RequestHandlerSelectors.basePackage("vn.rts.customs.eclare"),
						RequestHandlerSelectors.basePackage("vn.rts.customs.eclare")))
				.paths(PathSelectors.any()).build().apiInfo(metaData()).securitySchemes(getApiKeys(true))
				.securityContexts(Lists.newArrayList(securityContext()));
	}
}
