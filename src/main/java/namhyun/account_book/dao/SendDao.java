package namhyun.account_book.dao;

import namhyun.account_book.dto.SearchCondition;
import namhyun.account_book.dto.SendDto;

import java.util.List;

public interface SendDao {

    SendDto saveSend(SendDto sendDto);
    SendDto updateSend(SendDto sendDto);
    SendDto getSendById(Long id);
    List<SendDto> getSends(SearchCondition searchCondition);
}
