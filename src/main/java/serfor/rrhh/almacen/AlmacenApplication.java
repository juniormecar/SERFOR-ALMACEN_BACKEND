package serfor.rrhh.almacen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AlmacenApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(AlmacenApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AlmacenApplication.class);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				//HttpSecurity http = null;
				//http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated().and().csrf().disable();
				registry.addMapping("/*").allowedOrigins("").allowedMethods("").allowedHeaders("");
				//registry.addMapping("/*").allowedOrigins("https://test-ces.saludpol.gob.pe:32018").allowedMethods("").allowedHeaders("*");
				//registry.addMapping("/*").allowedOrigins("https://app-ces.saludpol.gob.pe:32018").allowedMethods("").allowedHeaders("*");
			}

		};
	}

}
