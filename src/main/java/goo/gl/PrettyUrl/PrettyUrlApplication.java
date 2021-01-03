package goo.gl.PrettyUrl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PrettyUrlApplication {
//	@Value("${pretty.url}")
//	public String prettyUrl;



	public static void main(String[] args) {
		SpringApplication.run(PrettyUrlApplication.class, args);
	}

}
