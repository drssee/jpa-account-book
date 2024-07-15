package namhyun.account_book.config;

import lombok.extern.log4j.Log4j2;
import namhyun.account_book.common.DummySendManager;
import namhyun.account_book.common.SendManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
@Log4j2
public class AppConfig {

    @Bean
    public SendManager sendManager() {
        // TODO sendManager 구현 해야함
        log.info("Create Dummy SendManager");
        return new DummySendManager();
    }
}
