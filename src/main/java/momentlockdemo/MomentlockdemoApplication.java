package momentlockdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MomentlockdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomentlockdemoApplication.class, args);
	}

}
