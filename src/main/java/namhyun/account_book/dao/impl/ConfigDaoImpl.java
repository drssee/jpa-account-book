package namhyun.account_book.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import namhyun.account_book.dao.ConfigDao;
import namhyun.account_book.domain.Config;
import namhyun.account_book.domain.Member;
import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.dto.SearchCondition;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConfigDaoImpl implements ConfigDao {

    @PersistenceContext
    private EntityManager em;

    private final ModelMapper modelMapper;

    @Override
    public ConfigDto getConfigById(Long id) {
        return null;
    }

    @Override
    public ConfigDto saveConfig(ConfigDto configDto) {
        configDto.setCreatedAt(LocalDateTime.now());
        configDto.setCreatedBy(configDto.getMemberDto().getId());
        MemberDto memberDto = configDto.getMemberDto();
        Member member = em.find(Member.class, memberDto.getId());
        if (member == null) {
            throw new RuntimeException("saveConfig - Member not found");
        }
        Config config = modelMapper.map(configDto, Config.class);
        config.setMember(member);
        em.persist(config);
        return modelMapper.map(config, ConfigDto.class);
    }

    @Override
    public ConfigDto updateConfig(ConfigDto configDto) {
        return null;
    }

    @Override
    public ConfigDto getConfigByMemberId(String memberId) {
        String query = "select c from Config c where c.member.id = :memberId";
        Config result = em.createQuery(query, Config.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
        return modelMapper.map(result, ConfigDto.class);
    }

    @Override
    public List<ConfigDto> getConfigs(SearchCondition searchCondition) {
        return List.of();
    }
}
