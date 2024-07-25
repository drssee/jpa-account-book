package namhyun.account_book.integrated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import namhyun.account_book.CommonInit;
import namhyun.account_book.dto.AccountBookDto;
import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.PayPurposeDto;
import namhyun.account_book.enums.UserType;
import namhyun.account_book.service.ConfigService;
import namhyun.account_book.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AccountBookTest {

    AccountBookDto accountBookDto;
    MemberDto memberDto;
    ConfigDto configDto;
    CommonInit commonInit = new CommonInit();

    @Autowired
    MemberService memberService;

    @Autowired
    ConfigService configService;

    int port = 8080;

    String baseUrl = "http://localhost:" + port;

    @Autowired
    TestRestTemplate restTemplate;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        accountBookDto = commonInit.initAccountBookDto();
        memberDto = commonInit.initMemberDto();
        configDto = commonInit.initConfigDto();
    }

    @Test
    @DisplayName("pay")
    @DirtiesContext
    public void pay() {
        restTemplate.postForEntity(baseUrl + "/member", memberDto, String.class);
        restTemplate.postForEntity(baseUrl + "/config", configDto, String.class);

        ResponseEntity<AccountBookDto> response = restTemplate.postForEntity(baseUrl + "/account", accountBookDto, AccountBookDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println("response = " + response.getBody());
    }

    @Test
    @DisplayName("get")
    @DirtiesContext
    public void get() {
        restTemplate.postForEntity(baseUrl + "/member", memberDto, String.class);
        restTemplate.postForEntity(baseUrl + "/config", configDto, String.class);
        AccountBookDto savedAccountBook = restTemplate.postForEntity(baseUrl + "/account", accountBookDto, AccountBookDto.class).getBody();

        ResponseEntity<AccountBookDto> result = restTemplate.getForEntity(baseUrl + "/account/" + savedAccountBook.getId(), AccountBookDto.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println("result = " + result.getBody());
    }

    @Test
    @DisplayName("update")
    @DirtiesContext
    public void update() {
        restTemplate.postForEntity(baseUrl + "/member", memberDto, String.class);
        restTemplate.postForEntity(baseUrl + "/config", configDto, String.class);
        AccountBookDto savedAccountBook = restTemplate.postForEntity(baseUrl + "/account", accountBookDto, AccountBookDto.class).getBody();

        savedAccountBook.setTitle("MODIFIED_TITLE");
        savedAccountBook.setPrice(savedAccountBook.getPrice() + 10000);

        HttpEntity<AccountBookDto> requestEntity = new HttpEntity<>(savedAccountBook);
        ResponseEntity<AccountBookDto> response = restTemplate.exchange(baseUrl + "/account", HttpMethod.PUT, requestEntity, AccountBookDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AccountBookDto updatedAccountBook = response.getBody();
        assertThat(updatedAccountBook.getTitle()).isEqualTo("MODIFIED_TITLE");
        assertThat(updatedAccountBook.getPrice()).isEqualTo(savedAccountBook.getPrice());
    }
}
