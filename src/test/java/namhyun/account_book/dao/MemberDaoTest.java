package namhyun.account_book.dao;

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

@SpringBootTest
@Transactional
public class MemberDaoTest {

    MemberDto memberDto;
    CommonInit commonInit = new CommonInit();

    @Autowired
    MemberDao memberDao;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        memberDto = commonInit.initMemberDto();
    }

    @Test
    @DisplayName("MemberDao.saveMember()")
    void saveMember() {
        MemberDto savedMember = memberDao.saveMember(memberDto);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getCreatedBy()).isNotNull();
        assertThat(savedMember.getCreatedAt()).isNotNull();
        assertThat(savedMember.getUseYn()).isEqualTo("Y");
    }

    @Test
    @DisplayName("MemberDao.updateMember()")
    void updateMember() {
        MemberDto savedMember = memberDao.saveMember(memberDto);

        String modifiedName = "MODIFIED_NAME";
        savedMember.setName(modifiedName);
        int modifiedAge = savedMember.getAge() + 10;
        savedMember.setAge(modifiedAge);
        UserType modifiedUserType = UserType.ADMIN;
        savedMember.setUserType(modifiedUserType);
        String modifiedUserYn = "N";
        savedMember.setUseYn(modifiedUserYn);

        MemberDto updatedMember = memberDao.updateMember(savedMember);
        commonInit.flush(em);

        MemberDto findMember = memberDao.getMemberById(updatedMember.getId());

        assertThat(findMember).isNotNull();
        commonInit.assertFindMemberDto(findMember, updatedMember);
    }
}
