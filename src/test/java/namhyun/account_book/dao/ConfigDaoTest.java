package namhyun.account_book.dao;

import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.MemberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

        Assertions.assertThat(savedConfig).isNotNull();
        Assertions.assertThat(savedConfig.getId()).isNotNull();
        Assertions.assertThat(savedConfig.getCreatedAt()).isNotNull();
        Assertions.assertThat(savedConfig.getCreatedBy()).isNotNull();
        Assertions.assertThat(savedConfig.getMemberDto().getId()).isNotNull();
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

        Assertions.assertThat(findConfig).isNotNull();
        Assertions.assertThat(findConfig.getId()).isNotNull();
        Assertions.assertThat(findConfig.getMemberDto().getId()).isEqualTo(savedMember.getId());
    }
}
