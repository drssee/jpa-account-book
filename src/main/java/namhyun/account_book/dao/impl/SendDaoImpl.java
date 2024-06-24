package namhyun.account_book.dao.impl;

import namhyun.account_book.dao.SendDao;
import namhyun.account_book.dto.SearchCondition;
import namhyun.account_book.dto.SendDto;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SendDaoImpl implements SendDao {

    @Override
    public SendDto getSendById(Long id) {
        return null;
    }

    @Override
    public SendDto saveSend(SendDto sendDto) {
        return null;
    }

    @Override
    public SendDto updateSend(SendDto sendDto) {
        return null;
    }

    @Override
    public List<SendDto> getSends(SearchCondition searchCondition) {
        return List.of();
    }
}
