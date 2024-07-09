package namhyun.account_book.common;

import namhyun.account_book.dto.SendDto;
import namhyun.account_book.dto.SendResult;

import java.util.List;

public interface SendManager {
    SendResult doSend(List<SendDto> sendDtoList);
}
