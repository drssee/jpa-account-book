package namhyun.account_book.config;

import lombok.extern.log4j.Log4j2;
import namhyun.account_book.common.DummySendManager;
import namhyun.account_book.common.SendManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
@Log4j2
public class TestConfig {

    @Bean
    public SendManager sendManager() {
        log.info("(Test) Create Dummy SendManager");
        return new DummySendManager();
    }
}
