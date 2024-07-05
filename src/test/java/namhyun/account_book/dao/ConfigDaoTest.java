package namhyun.account_book.dao;

import namhyun.account_book.CommonInit;
import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ConfigDaoTest {

    MemberDto memberDto;
    ConfigDto configDto;
    CommonInit commonInit = new CommonInit();

    @Autowired
    private ConfigDao configDao;

    @Autowired
    private MemberDao memberDao;

    @BeforeEach
    void init() {
        memberDto = commonInit.initMemberDto();
        configDto = commonInit.initConfigDto();
    }

    @Test
    @DisplayName("ConfigDao.saveConfig()")
    void saveConfig() {
        // 멤버저장
        memberDao.saveMember(memberDto);
        // 설정저장
        ConfigDto savedConfig = configDao.saveConfig(configDto);

        assertThat(savedConfig).isNotNull();
        assertThat(savedConfig.getId()).isNotNull();
        assertThat(savedConfig.getCreatedAt()).isNotNull();
        assertThat(savedConfig.getCreatedBy()).isNotNull();
        assertThat(savedConfig.getMemberDto().getId()).isNotNull();
    }

    @Test
    @DisplayName("ConfigDao.getConfigByMemberId()")
    void getConfigByMemberId() {
        // 멤버저장
        MemberDto savedMember = memberDao.saveMember(memberDto);
        // 설정저장
        ConfigDto savedConfig = configDao.saveConfig(configDto);
        // 조회테스트
        ConfigDto findConfig = configDao.getConfigByMemberId(savedMember.getId());

        assertThat(findConfig).isNotNull();
        assertThat(findConfig.getId()).isNotNull();
        assertThat(findConfig.getMemberDto().getId()).isEqualTo(savedMember.getId());
    }
}
