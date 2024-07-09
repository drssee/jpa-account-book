package namhyun.account_book.service;

import namhyun.account_book.dto.SendDto;
import namhyun.account_book.dto.SendResult;
import namhyun.account_book.enums.SendType;

import java.time.LocalDateTime;
import java.util.List;

public interface SendService {

    SendDto saveSend(SendDto sendDto);
    SendDto createSend(SendType sendType, String customMsg, LocalDateTime sendTime ,String memberId);
    List<SendDto> getSendListByMemberId(String memberId);
    SendResult doSend();
}
