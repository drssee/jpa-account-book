package namhyun.account_book.service;

import namhyun.account_book.CommonInit;
import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.MemberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ConfigServiceTest {

    CommonInit commonInit = new CommonInit();
    ConfigDto configDto;

    @Autowired
    ConfigService configService;

    @Autowired
    MemberService memberService;

    @BeforeEach
    void init() {
        configDto = commonInit.initConfigDto();
    }

    @Test
    @DisplayName("ConfigService.saveConfig()")
    void saveConfig() {
        memberService.saveMember(configDto.getMemberDto());
        ConfigDto savedConfig = configService.saveConfig(configDto);

        assertThat(savedConfig).isNotNull();
        assertThat(savedConfig.getId()).isNotNull();
        assertThat(savedConfig.getMemberDto().getId()).isEqualTo(configDto.getMemberDto().getId());
        assertThat(savedConfig.getCreatedBy()).isNotNull();
        assertThat(savedConfig.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("ConfigService.getConfigByMemberId()")
    void getConfigByMemberId() {
        memberService.saveMember(configDto.getMemberDto());
        ConfigDto savedConfig = configService.saveConfig(configDto);
        ConfigDto findConfigDto = configService.getConfigByMemberId(configDto.getMemberDto().getId());

        assertThat(findConfigDto).isNotNull();
        assertThat(findConfigDto.getId()).isNotNull();
        assertThat(findConfigDto.getId()).isEqualTo(savedConfig.getId());
        assertThat(findConfigDto.getMemberDto().getId()).isNotNull();
        assertThat(findConfigDto.getMemberDto().getId()).isEqualTo(savedConfig.getMemberDto().getId());
        assertThat(findConfigDto.getCreatedAt()).isNotNull();
        assertThat(findConfigDto.getCreatedAt()).isEqualTo(savedConfig.getCreatedAt());
        assertThat(findConfigDto.getCreatedBy()).isNotNull();
        assertThat(findConfigDto.getCreatedBy()).isEqualTo(savedConfig.getCreatedBy());
        assertThat(findConfigDto.getPayLimit()).isNotEqualTo(0L);
        assertThat(findConfigDto.getCustomMsg()).isEqualTo(savedConfig.getCustomMsg());
        assertThat(findConfigDto.getCustomSendType()).isEqualTo(savedConfig.getCustomSendType());
    }
}
