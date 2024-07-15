package namhyun.account_book.integrated;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import namhyun.account_book.CommonInit;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.enums.UserType;
import namhyun.account_book.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MemberTest {

    MemberDto memberDto;
    CommonInit commonInit = new CommonInit();

    @Autowired
    MemberService memberService;

    int port = 8080;

    String baseUrl = "http://localhost:" + port;

    @Autowired
    TestRestTemplate restTemplate;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        memberDto = commonInit.initMemberDto();
    }

    @AfterEach
    void tearDown() {
        em.createQuery("delete from Member").executeUpdate();
        commonInit.flush(em);
    }

    @Test
    @DisplayName("멤버 생성 성공")
    @DirtiesContext
    public void createMember_success() {
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/member", memberDto, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("멤버 생성 실패1")
    @DirtiesContext
    public void createMember_fail1() {
        memberDto.setId(null);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/member", memberDto, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("멤버 생성 실패2")
    @DirtiesContext
    public void createMember_fail2() {
        memberDto.setId("10글자가넘는아이디입니다 공백도 있어요");
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/member", memberDto, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("멤버 생성 실패3")
    @DirtiesContext
    public void createMember_fail3() {
        restTemplate.postForEntity(baseUrl + "/member", memberDto, String.class);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/member", memberDto, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("멤버 수정 성공")
    @DirtiesContext
    public void updateMember_success() {
        restTemplate.postForEntity(baseUrl + "/member", memberDto, String.class);
        MemberDto findMember = restTemplate.getForObject(baseUrl + "/member/" + memberDto.getId(), MemberDto.class);
        findMember.setName("MODIFIED_NAME");
        findMember.setAge(findMember.getAge() + 1);
        findMember.setUserType(UserType.ADMIN);

        HttpEntity<MemberDto> requestEntity = new HttpEntity<>(findMember);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/member", HttpMethod.PUT, requestEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("멤버 수정 실패")
    @DirtiesContext
    public void updateMember_fail() {
        MemberDto findMember = null;

        HttpEntity<MemberDto> requestEntity = new HttpEntity<>(findMember);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/member", HttpMethod.PUT, requestEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        System.out.println(response.getBody());
    }

    @Test
    @DisplayName("멤버 삭제 성공")
    @DirtiesContext
    public void deleteMember_success() {
        restTemplate.postForEntity(baseUrl + "/member", memberDto, String.class);
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/member/" + memberDto.getId(),
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("멤버 삭제 실패1(존재하지 않는 유저)")
    @DirtiesContext
    public void deleteMember_fail1() {
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/member/" + memberDto.getId(),
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("멤버 삭제 실패2(이미 삭제된 유저)")
    @DirtiesContext
    public void deleteMember_fail2() {
        restTemplate.postForEntity(baseUrl + "/member", memberDto, String.class);
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        restTemplate.exchange(
                baseUrl + "/member/" + memberDto.getId(),
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );

        // 유저 삭제 후 재요청
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/member/" + memberDto.getId(),
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
