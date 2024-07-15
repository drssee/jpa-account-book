package namhyun.account_book.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import namhyun.account_book.CommonInit;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    MemberDto memberDto;
    CommonInit commonInit = new CommonInit();

    @Autowired
    MemberService memberService;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        memberDto = commonInit.initMemberDto();
    }

    @Test
    @DisplayName("MemberService.saveMember()")
    void saveMember() {
        MemberDto savedMember = memberService.saveMember(memberDto);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getCreatedBy()).isNotNull();
        assertThat(savedMember.getCreatedAt()).isNotNull();
        assertThat(savedMember.getUseYn()).isEqualTo("Y");
    }

    @Test
    @DisplayName("MemberDao.updateMember()")
    void updateMember() {
        MemberDto savedMember = memberService.saveMember(memberDto);

        String modifiedName = "MODIFIED_NAME";
        savedMember.setName(modifiedName);
        int modifiedAge = savedMember.getAge() + 10;
        savedMember.setAge(modifiedAge);
        UserType modifiedUserType = UserType.ADMIN;
        savedMember.setUserType(modifiedUserType);
        String modifiedUserYn = "N";
        savedMember.setUseYn(modifiedUserYn);

        MemberDto updatedMember = memberService.updateMember(savedMember);
        commonInit.flush(em);

        MemberDto findMember = memberService.getMemberById(updatedMember.getId());

        assertThat(findMember).isNotNull();
        commonInit.assertFindMemberDto(findMember, updatedMember);
    }

    @Test
    @DisplayName("MemberService.deleteMember()")
    void deleteMember() {
        MemberDto savedMember = memberService.saveMember(memberDto);
        savedMember.setUseYn("Y");
        MemberDto updatedMember = memberService.updateMember(savedMember);
        memberService.deleteMember(savedMember.getId());
        commonInit.flush(em);
        MemberDto findMember = memberService.getMemberById(updatedMember.getId());
        assertThat(findMember.getUseYn()).isEqualTo("N");
    }

    @Test
    @DisplayName("MemberService.deleteMember()_already_deleted")
    void deleteMember_already_deleted() {
        MemberDto savedMember = memberService.saveMember(memberDto);
        savedMember.setUseYn("N");
        memberService.updateMember(savedMember);
        commonInit.flush(em);
        assertThatThrownBy(()->{
            memberService.deleteMember(savedMember.getId());
        })
                .isInstanceOf(RuntimeException.class)
                        .hasMessage("deleteMember() - already deleted member");
    }
}
