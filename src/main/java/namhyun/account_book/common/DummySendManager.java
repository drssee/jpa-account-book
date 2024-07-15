package namhyun.account_book.common;

import lombok.extern.log4j.Log4j2;
import namhyun.account_book.dto.SendDto;
import namhyun.account_book.dto.SendResult;

import java.util.List;

@Log4j2
public class DummySendManager implements SendManager {

    @Override
    public SendResult doSend(List<SendDto> sendDtoList) {
        log.info("send start");
        SendResult sendResult = new SendResult();
        sendResult.setSize(sendDtoList.size());
        // 전부 성공한다고 가정
        sendResult.setSuccess(sendDtoList.size());
        sendResult.setFail(0);
        sendDtoList.forEach(e -> {
            log.info(e.getId());
            log.info(e.getSendTime());
            sendResult.addSendTimes(e.getId(), e.getSendTime());
        });
        log.info("send end");

        return sendResult;
    }
}
