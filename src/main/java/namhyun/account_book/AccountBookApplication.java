package namhyun.account_book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccountBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountBookApplication.class, args);
	}

}
